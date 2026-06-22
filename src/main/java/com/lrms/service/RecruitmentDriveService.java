package com.lrms.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lrms.dto.RecruitmentDriveRequestDTO;
import com.lrms.dto.RecruitmentDriveResponseDTO;
import com.lrms.entity.Booking;
import com.lrms.entity.Lab;
import com.lrms.entity.RecruitmentDrive;
import com.lrms.entity.User;
import com.lrms.enums.BookingStatus;
import com.lrms.exception.ConflictException;
import com.lrms.exception.IllegalStartException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.repository.BookingRepository;
import com.lrms.repository.LabRepository;
import com.lrms.repository.RecruitmentDriveRepository;
import com.lrms.repository.UserRepository;

@Service
public class RecruitmentDriveService {
	private final RecruitmentDriveRepository recruitmentDriveRepository;
	private final LabRepository labRepository;
	private final UserRepository userRepository;
	private final BookingRepository bookingRepository;
	
	public RecruitmentDriveService(RecruitmentDriveRepository recruitmentDriveRepository,
			LabRepository labRepository,
			UserRepository userRepository,
			BookingRepository bookingRepository) {
		this.recruitmentDriveRepository = recruitmentDriveRepository;
		this.labRepository = labRepository;
		this.userRepository = userRepository;
		this.bookingRepository = bookingRepository;
	}
	
	public List<RecruitmentDriveResponseDTO> getAllDrives() {
	    return recruitmentDriveRepository.findAll()
	            .stream()
	            .map(this::mapToResponse)
	            .toList();
	}

	public RecruitmentDriveResponseDTO getDriveById(Long id) {
	    RecruitmentDrive drive =
	            recruitmentDriveRepository.findById(id)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException(
	                            "Drive not found"));

	    return mapToResponse(drive);
	}
	
	public RecruitmentDriveResponseDTO createDrive(RecruitmentDriveRequestDTO dto, String email) {
		User creator = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user not found"));
		
		List<Lab> labs = labRepository.findAll();
		Lab selectedLab = null;
		LocalDateTime driveStart = LocalDateTime.of(
		        dto.getDate(),
		        dto.getStartTime());
		LocalDateTime driveEnd = LocalDateTime.of(
		        dto.getDate(),
		        dto.getEndTime());
		if(driveStart.isAfter(driveEnd)) {
		    throw new IllegalStartException(
		        "Start time must be before end time");
		}
		for(Lab lab : labs) {
			if(lab.getCapacity() < dto.getExpectedHeadCount())
				continue;
			if(lab.getInternetBandwidth() < dto.getRequiredBandwidth())
				continue;
			if(!lab.isPowerBackup() && dto.isRequiresPowerBackup())
				continue;
			if(!lab.isAvReady() && dto.isRequiresAV())
				continue;
			List<Booking> approvedBookings = bookingRepository.findByLabIdAndStatus(lab.getId(), BookingStatus.APPROVED);
			boolean conflict = false;
			for(Booking booking: approvedBookings) {
				if(driveStart.isBefore(booking.getEndTime()) && 
						driveEnd.isAfter(booking.getStartTime())) {
					conflict = true;
					break;
				}
			}
			if(conflict) {
				continue;
			}
			selectedLab = lab;
			break;
		}
		
		if(selectedLab == null) {
			throw new ConflictException("No suitable lab available");
		}
		
		RecruitmentDrive drive = new RecruitmentDrive();
		drive.setTitle(dto.getTitle());
		drive.setDate(dto.getDate());
		drive.setStartTime(dto.getStartTime());
		drive.setEndTime(dto.getEndTime());
		drive.setExpectedHeadCount(dto.getExpectedHeadCount());
		drive.setRequiredBandwidth(dto.getRequiredBandwidth());
		drive.setRequiresPowerBackup(dto.isRequiresPowerBackup());
		drive.setRequiresAV(dto.isRequiresAV());
		drive.setAssignedLab(selectedLab);
		drive.setCreatedBy(creator);
		
		RecruitmentDrive savedDrive = recruitmentDriveRepository.save(drive);
		
		Booking booking = new Booking();
		booking.setStartTime(driveStart);
		booking.setEndTime(driveEnd);
		booking.setPurpose("Recruitment Drive - "+dto.getTitle());
		booking.setStatus(BookingStatus.APPROVED);
		booking.setLab(selectedLab);
		booking.setRequester(creator);
		bookingRepository.save(booking);
		
		return mapToResponse(savedDrive);
		
	}
	
	private RecruitmentDriveResponseDTO mapToResponse(RecruitmentDrive dto) {
		RecruitmentDriveResponseDTO response = new RecruitmentDriveResponseDTO();
		Lab lab = dto.getAssignedLab();
		response.setId(dto.getId());
		response.setTitle(dto.getTitle());
		response.setDate(dto.getDate());
		response.setStartTime(dto.getStartTime());
		response.setEndTime(dto.getEndTime());
		response.setExpectedHeadCount(dto.getExpectedHeadCount());
		response.setRequiredBandwidth(dto.getRequiredBandwidth());
		response.setRequiresPowerBackup(dto.isRequiresPowerBackup());
		response.setRequiresAV(dto.isRequiresAV());
		response.setAssignedLabId(lab.getId());
		response.setRoomNumber(lab.getRoomNumber());
		response.setLabName(lab.getName());
		
		return response;
	}
}
