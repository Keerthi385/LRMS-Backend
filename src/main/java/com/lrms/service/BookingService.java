package com.lrms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lrms.dto.BookingRequestDTO;
import com.lrms.dto.BookingResponseDTO;
import com.lrms.entity.Booking;
import com.lrms.entity.Lab;
import com.lrms.entity.User;
import com.lrms.enums.BookingStatus;
import com.lrms.exception.ConflictException;
import com.lrms.exception.IllegalStartException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.repository.BookingRepository;
import com.lrms.repository.LabRepository;
import com.lrms.repository.UserRepository;

@Service
public class BookingService {
	private final BookingRepository bookingRepository;
	private final LabRepository labRepository;
	private final UserRepository userRepository;
	
	public BookingService(
			BookingRepository bookingRepository,
			LabRepository labRepository,
			UserRepository userRepository) {
		
		this.bookingRepository = bookingRepository;
		this.labRepository = labRepository;
		this.userRepository = userRepository;
	}
	
	public List<BookingResponseDTO> getAllBookings() {
		List<BookingResponseDTO> response = new ArrayList<>();
		List<Booking> bookings = bookingRepository.findAll();
		for(Booking booking : bookings) {
			response.add(mapToBookingResponseDTO(booking));
		}
		return response;
	}
	
	public BookingResponseDTO getBookingById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
		return mapToBookingResponseDTO(booking);
	}
	
	public BookingResponseDTO createBooking(BookingRequestDTO dto, String email) {
		
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Lab lab = labRepository.findById(dto.getLabId())
				.orElseThrow(() -> new ResourceNotFoundException("Lab not found"));
		
		if(!dto.getStartTime().isBefore(dto.getEndTime())) {
		    throw new IllegalStartException(
		        "Start time must be before end time");
		}
		
		List<Booking> approvedBookings = bookingRepository.findByLabIdAndStatus(
				dto.getLabId(),
				BookingStatus.APPROVED);
		
		for(Booking booking: approvedBookings) {
			if(dto.getStartTime().isBefore(booking.getEndTime()) && 
					dto.getEndTime().isAfter(booking.getStartTime())) {
				throw new ConflictException("Lab already booked for this time slot");
			}
		}
		
		Booking booking = new Booking();
		
		booking.setStartTime(dto.getStartTime());
		booking.setEndTime(dto.getEndTime());
		booking.setStatus(BookingStatus.REQUESTED);
		booking.setPurpose(dto.getPurpose());
		booking.setLab(lab);
		booking.setRequester(user);
		
		Booking savedBooking = bookingRepository.save(booking);
		
		return mapToBookingResponseDTO(savedBooking);
		
		
	}
	
	public BookingResponseDTO approveBooking(Long id, String email) {
		
		User approver = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Long approverId = approver.getId();
		
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
		
		if(booking.getRequester().getId().equals(approverId)) {
	        throw new IllegalStateException("You cannot approve a booking you created.");
	    }
		
		// Conflict check: ensure no overlap with existing approved bookings
	    List<Booking> approvedBookings = bookingRepository.findByLabIdAndStatus(
	            booking.getLab().getId(),
	            BookingStatus.APPROVED
	    );

	    for (Booking existing : approvedBookings) {
	        if (booking.getStartTime().isBefore(existing.getEndTime()) &&
	            booking.getEndTime().isAfter(existing.getStartTime())) {
	            throw new ConflictException("Cannot approve: Lab already booked for this time slot.");
	        }
	    }
		
		booking.setStatus(BookingStatus.APPROVED);
		
		Booking savedBooking = bookingRepository.save(booking);
		
		return mapToBookingResponseDTO(savedBooking);
	}
	
	public BookingResponseDTO rejectBooking(Long id, String email) {
		
		User rejecter = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));
		
		Long rejecterId = rejecter.getId();
		
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
		
		if(booking.getRequester().getId().equals(rejecterId)) {
	        throw new IllegalStateException("You cannot reject a booking you created.");
	    }
		System.out.println(rejecterId + " " + booking.getRequester().getId());
		booking.setStatus(BookingStatus.REJECTED);
		
		Booking savedBooking = bookingRepository.save(booking);
		
		return mapToBookingResponseDTO(savedBooking);
	}
	
	private BookingResponseDTO mapToBookingResponseDTO(Booking booking) {
		BookingResponseDTO dto = new BookingResponseDTO();
		dto.setId(booking.getId());
		dto.setLabId(booking.getLab().getId());
		dto.setRoomNumber(booking.getLab().getRoomNumber());
		dto.setRequesterId(booking.getRequester().getId());
		dto.setRequesterEmail(booking.getRequester().getEmail());
		dto.setReuesterName(booking.getRequester().getName());
		dto.setStartTime(booking.getStartTime());
		dto.setEndTime(booking.getEndTime());
		dto.setPurpose(booking.getPurpose());
		dto.setStatus(booking.getStatus());
		
		return dto;
	}
	
}
