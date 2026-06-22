package com.lrms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lrms.dto.ComputerRequestDTO;
import com.lrms.dto.ComputerResponseDTO;
import com.lrms.entity.Computer;
import com.lrms.entity.Lab;
import com.lrms.exception.ConflictException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.repository.ComputerRepository;
import com.lrms.repository.LabRepository;

@Service
public class ComputerService {
	
	private final ComputerRepository computerRepository;
	private final LabRepository labRepository;
	
	public ComputerService(
			ComputerRepository computerRepository,
			LabRepository labRepository) {
		this.computerRepository = computerRepository;
		this.labRepository = labRepository;
	}
	
	public List<ComputerResponseDTO> getAllComputers() {
		List<Computer> computers =  computerRepository.findAll();
		List<ComputerResponseDTO> response = new ArrayList<>();
		for(Computer computer : computers) {
			response.add(mapToComputerResponseDTO(computer));
		}
		return response;
	}
	
	public ComputerResponseDTO getComputerById(Long id) {
		Computer computer = computerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Computer not found"));
		return mapToComputerResponseDTO(computer);
	}
	
	public List<ComputerResponseDTO> getComputersByLabId(Long labId) {
		List<Computer> computers =  computerRepository.findByLabId(labId);
		List<ComputerResponseDTO> response = new ArrayList<>();
		for(Computer computer : computers) {
			response.add(mapToComputerResponseDTO(computer));
		}
		return response;
	}
	
	public ComputerResponseDTO createComputer(ComputerRequestDTO dto) {
		
		if(computerRepository.existsByTag(dto.getTag())) {
			throw new ConflictException("Computer with same tag already exists");
		}
		
		Lab lab = labRepository.findById(dto.getLabId())
				.orElseThrow(() -> new ResourceNotFoundException("Lab not found"));
		
		Computer computer = new Computer();
		
		computer.setTag(dto.getTag());
		computer.setAudioReady(dto.isAudioReady());
		computer.setVideoReady(dto.isVideoReady());
		computer.setNicSpeed(dto.getNicSpeed());
		computer.setStatus(dto.getStatus());
		computer.setLab(lab);
		
		Computer savedComputer = computerRepository.save(computer);
		
		return mapToComputerResponseDTO(savedComputer);
		
	}
	
	public ComputerResponseDTO updateComputer(Long id, ComputerRequestDTO dto) {
		
		Computer computer = computerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Computer not found"));
		
		Lab lab = labRepository.findById(dto.getLabId())
				.orElseThrow(() -> new ResourceNotFoundException("Lab not found"));
		
		//Check if computer with same tag exists
		if (computerRepository.existsByTag(dto.getTag()) && 
		        !computer.getTag().equals(dto.getTag())) {
		        throw new ConflictException("Computer with same tag already exists");
		}
		
		computer.setTag(dto.getTag());
		computer.setAudioReady(dto.isAudioReady());
		computer.setVideoReady(dto.isVideoReady());
		computer.setNicSpeed(dto.getNicSpeed());
		computer.setStatus(dto.getStatus());
		computer.setLab(lab);
		
		Computer updatedComputer = computerRepository.save(computer);
		
		return mapToComputerResponseDTO(updatedComputer);
	}
	
	public void deleteComputer(Long id) {
		Computer computer = computerRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Computer not found"));
		computerRepository.delete(computer);
	}
	
	private ComputerResponseDTO mapToComputerResponseDTO(Computer computer) {
		ComputerResponseDTO response = new ComputerResponseDTO();
		
		response.setId(computer.getId());
		response.setTag(computer.getTag());
		response.setAudioReady(computer.isAudioReady());
		response.setVideoReady(computer.isVideoReady());
		response.setNicSpeed(computer.getNicSpeed());
		response.setStatus(computer.getStatus());
		response.setLabId(computer.getLab().getId());
		response.setRoomNumber(computer.getLab().getRoomNumber());
		response.setLabName(computer.getLab().getName());
		
		return response;
	}

}
