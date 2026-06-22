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

import com.lrms.dto.IssueRequestDTO;
import com.lrms.dto.IssueResponseDTO;
import com.lrms.service.IssueService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/issues")
public class IssueController {
	
	private final IssueService issueService;
	
	public IssueController(IssueService issueService) {
		this.issueService = issueService;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'LAB_ASSISSTANT', 'STUDENT')")
	@PostMapping
	public IssueResponseDTO createIssue(@Valid @RequestBody IssueRequestDTO dto, Principal principal) {
		return issueService.createIssue(dto, principal.getName());
	}
	
	@GetMapping
	public List<IssueResponseDTO> getAllIssues() {
		return issueService.getAllIssues();
	}
	
	@GetMapping("/{id}")
	public IssueResponseDTO getIssueById(@PathVariable Long id) {
		return issueService.getIssueById(id);
	}
	
	@GetMapping("/labs/{labId}")
	public List<IssueResponseDTO> getIssuesByLab(@PathVariable Long labId) {
		return issueService.getIssuesByLab(labId);
	}
	
	@GetMapping("computers/{computerId}")
	public List<IssueResponseDTO> getIssuesByComputer(@PathVariable Long computerId) {
		return issueService.getIssuesByComputer(computerId);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LAB_ASSISTANT')")
	@PutMapping("/{id}/start-work")
	public IssueResponseDTO startWork(@PathVariable Long id) {
		return issueService.startWork(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','LAB_ASSISTANT')")
	@PutMapping("/{id}/escalate/faculty")
	public IssueResponseDTO escalateToFaculty(@PathVariable Long id) {
		return issueService.escalateToFaculty(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY_COORDINATOR')")
	@PutMapping("/{id}/escalate/hod")
	public IssueResponseDTO escalateToHod(@PathVariable Long id) {
		return issueService.escalateToHod(id);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','FACULTY_COORDINATOR','HOD')")
	@PutMapping("/{id}/resolve")
	public IssueResponseDTO resolveIssue(@PathVariable Long id) {
		return issueService.resolveIssue(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}/close")
	public IssueResponseDTO closeIssue(@PathVariable Long id) {
		return issueService.closeIssue(id);
	}
}

