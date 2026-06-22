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

import com.lrms.dto.ComputerRequestDTO;
import com.lrms.dto.ComputerResponseDTO;
import com.lrms.service.ComputerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/computers")
public class ComputerController {
	
	private final ComputerService computerService;
	
	public ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	@GetMapping
	public List<ComputerResponseDTO> getAllComputers(){
		return computerService.getAllComputers();
	}
	
	@GetMapping("/lab/{labId}")
	public List<ComputerResponseDTO> getComputersByLabId(@PathVariable Long labId) {
		return computerService.getComputersByLabId(labId);
	}
	
	@GetMapping("/{id}")
	public ComputerResponseDTO getComputerById(@PathVariable Long id) {
		return computerService.getComputerById(id);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'LAB_ASSISTANT')")
	public ComputerResponseDTO createComputer(
			@Valid @RequestBody ComputerRequestDTO dto) {
		return computerService.createComputer(dto);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'LAB_ASSISTANT')")
	@PutMapping("/{id}")
	public ComputerResponseDTO updateComputer(
			@PathVariable Long id,
			@Valid @RequestBody ComputerRequestDTO dto) {
		return computerService.updateComputer(id, dto);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void deleteComputer(@PathVariable Long id) {
		computerService.deleteComputer(id);
	}
}
