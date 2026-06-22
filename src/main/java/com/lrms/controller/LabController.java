package com.lrms.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrms.dto.LabRequestDTO;
import com.lrms.dto.LabResponseDTO;
import com.lrms.entity.Lab;
import com.lrms.service.LabService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/labs")
public class LabController {
	
	private final LabService labService;
	
	public LabController(LabService labService) {
		this.labService = labService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public LabResponseDTO createLab(@Valid @RequestBody LabRequestDTO dto) {
		return labService.createLab(dto);
	}
	
	@GetMapping
	public List<Lab> getAllLabs() {
		return labService.getAllLabs();
	}
	
	@GetMapping("/{roomNumber}")
	public LabResponseDTO getLabByRoomNumber(@PathVariable Long roomNumber) {
		return labService.getLabByRoomNumber(roomNumber);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{roomNumber}")
	public LabResponseDTO updateLab(@PathVariable Long roomNumber,@Valid @RequestBody LabRequestDTO dto) {
		return labService.updateLab(roomNumber, dto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{roomNumber}")
	public void deleteLab(@PathVariable Long roomNumber) {
		labService.deleteLab(roomNumber);
	}
	
}
