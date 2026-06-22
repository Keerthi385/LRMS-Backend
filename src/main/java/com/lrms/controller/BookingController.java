package com.lrms.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrms.dto.BookingRequestDTO;
import com.lrms.dto.BookingResponseDTO;
import com.lrms.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	private final BookingService bookingService;
	
	public BookingController(BookingService bookingService) {
		this.bookingService = bookingService;
	}
	
	@PostMapping
	public BookingResponseDTO createBooking(@Valid @RequestBody BookingRequestDTO dto, Principal principal) {
		return bookingService.createBooking(dto, principal.getName());
	}
	
	@GetMapping
	public List<BookingResponseDTO> getAllBookings() {
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/{id}")
	public BookingResponseDTO getBookingById(@PathVariable Long id) {
		return bookingService.getBookingById(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'FACULTY_COORDINATOR')")
	@PutMapping("/approve/{id}")
	public BookingResponseDTO approveBooking(@PathVariable Long id, Principal principal) {
		return bookingService.approveBooking(id, principal.getName());
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'FACULTY_COORDINATOR')")
	@PutMapping("/reject/{id}")
	public BookingResponseDTO rejectBooking(@PathVariable Long id, Principal principal) {
		return bookingService.rejectBooking(id, principal.getName());
	}
}
