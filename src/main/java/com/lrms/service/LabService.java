package com.lrms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lrms.dto.LabRequestDTO;
import com.lrms.dto.LabResponseDTO;
import com.lrms.entity.Lab;
import com.lrms.exception.ConflictException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.repository.LabRepository;

@Service
public class LabService {
	private final LabRepository labRepository;
	
	public LabService(LabRepository labRepository) {
		this.labRepository = labRepository;
	}
	
	public List<Lab> getAllLabs() {
		return labRepository.findAll();
	}
	
	public LabResponseDTO createLab(LabRequestDTO dto) {
		if(labRepository.existsByRoomNumber(dto.getRoomNumber())) {
			throw new ConflictException("Room Number already exists!");
		}
		
		Lab lab = new Lab();
		lab.setRoomNumber(dto.getRoomNumber());
		lab.setName(dto.getName());
		lab.setLocation(dto.getLocation());
		lab.setCapacity(dto.getCapacity());
		lab.setPowerBackup(dto.isPowerBackup());
		lab.setInternetBandwidth(dto.getInternetBandwidth());
		lab.setAvReady(dto.isAvReady());
		
		Lab savedLab = labRepository.save(lab);
		
		return mapToLabResponseDTO(savedLab);
	}
	
	public LabResponseDTO getLabByRoomNumber(Long roomNumber) {
		Lab lab = labRepository.findByRoomNumber(roomNumber)
				.orElseThrow(() -> new ResourceNotFoundException("Lab not found"));
		
		return mapToLabResponseDTO(lab);
	}
	
	public LabResponseDTO updateLab(Long roomNumber, LabRequestDTO dto) {
	    Lab lab = labRepository.findByRoomNumber(roomNumber)
	            .orElseThrow(() -> new ResourceNotFoundException("Lab not found"));

	    // Update fields
	    lab.setName(dto.getName());
	    lab.setLocation(dto.getLocation());
	    lab.setCapacity(dto.getCapacity());
	    lab.setPowerBackup(dto.isPowerBackup());
	    lab.setInternetBandwidth(dto.getInternetBandwidth());
	    lab.setAvReady(dto.isAvReady());

	    Lab updatedLab = labRepository.save(lab);

	    return mapToLabResponseDTO(updatedLab);
	}
	
	public void deleteLab(Long roomNumber) {
	    Lab lab = labRepository.findByRoomNumber(roomNumber)
	            .orElseThrow(() -> new ResourceNotFoundException("Lab not found"));

	    labRepository.delete(lab);
	}
	
	private LabResponseDTO mapToLabResponseDTO(Lab lab) {
		LabResponseDTO response = new LabResponseDTO();
		response.setId(lab.getId());
		response.setRoomNumber(lab.getRoomNumber());
		response.setName(lab.getName());
		response.setLocation(lab.getLocation());
		response.setCapacity(lab.getCapacity());
		response.setPowerBackup(lab.isPowerBackup());
		response.setInternetBandwidth(lab.getInternetBandwidth());
		response.setAvReady(lab.isAvReady());
		
		return response;
	}


}
