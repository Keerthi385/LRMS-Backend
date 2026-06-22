package com.lrms.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrms.dto.RecruitmentDriveRequestDTO;
import com.lrms.dto.RecruitmentDriveResponseDTO;
import com.lrms.service.RecruitmentDriveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recruitment-drives")
public class RecruitmentDriveController {
	private final RecruitmentDriveService recruitmentDriveService;
	
	public RecruitmentDriveController(RecruitmentDriveService recruitmentDriveService) {
		this.recruitmentDriveService = recruitmentDriveService;
	}
	
	@GetMapping
	public List<RecruitmentDriveResponseDTO> getAllDrives() {
	    return recruitmentDriveService.getAllDrives();
	}

	@GetMapping("/{id}")
	public RecruitmentDriveResponseDTO getDriveById(
	        @PathVariable Long id) {

	    return recruitmentDriveService.getDriveById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'FACULTY_COORDINATOR')")
	public RecruitmentDriveResponseDTO createDrive(@Valid @RequestBody RecruitmentDriveRequestDTO dto, Principal principal) {
		return recruitmentDriveService.createDrive(dto, principal.getName());
	}
}
