package com.lrms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lrms.dto.IssueRequestDTO;
import com.lrms.dto.IssueResponseDTO;
import com.lrms.entity.Computer;
import com.lrms.entity.Issue;
import com.lrms.entity.User;
import com.lrms.enums.IssueStatus;
import com.lrms.exception.ConflictException;
import com.lrms.exception.IllegalTransitionException;
import com.lrms.exception.ResourceNotFoundException;
import com.lrms.repository.ComputerRepository;
import com.lrms.repository.IssueRepository;
import com.lrms.repository.LabRepository;
import com.lrms.repository.UserRepository;

@Service
public class IssueService {
	private final IssueRepository issueRepository;
	private final ComputerRepository computerRepository;
	private final LabRepository labRepository;
	private final UserRepository userRepository;
	
	public IssueService(
			IssueRepository issueRepository,
			ComputerRepository computerRepository,
			UserRepository userRepository,
			LabRepository labRepository) {
		this.issueRepository = issueRepository;
		this.computerRepository = computerRepository;
		this.userRepository = userRepository;
		this.labRepository = labRepository;
	}
	
	public List<IssueResponseDTO> getAllIssues() {
		List<IssueResponseDTO> response = new ArrayList<>();
		List<Issue> issues = issueRepository.findAll();
		for(Issue issue : issues) {
			response.add(mapToIssueResponseDTO(issue));
		}
		return response;
	}
	
	public IssueResponseDTO getIssueById(Long id) {
		Issue issue = issueRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		return mapToIssueResponseDTO(issue);
	}
	
	public List<IssueResponseDTO> getIssuesByLab(Long labId) {
		List<IssueResponseDTO> response = new ArrayList<>();
		List<Issue> issues = issueRepository.findByLabId(labId);
		for(Issue issue : issues) {
			response.add(mapToIssueResponseDTO(issue));
		}
		return response;
	}
	
	public List<IssueResponseDTO> getIssuesByComputer(Long computerId) {
		List<IssueResponseDTO> response = new ArrayList<>();
		List<Issue> issues = issueRepository.findByComputerId(computerId);
		for(Issue issue : issues) {
			response.add(mapToIssueResponseDTO(issue));
		}
		return response;
	}
	
	public IssueResponseDTO createIssue(IssueRequestDTO dto, String email) {
	    if (dto.getLabId() == null && dto.getComputerId() == null) {
	        throw new ConflictException("Invalid Issue: Both Lab ID and Computer ID cannot be empty");
	    }

	    User reporter = userRepository.findByEmail(email)
	            .orElseThrow(() -> new ResourceNotFoundException("Reporter not found"));

	    Issue issue = new Issue();
	    issue.setTitle(dto.getTitle());
	    issue.setDescription(dto.getDescription());
	    issue.setStatus(IssueStatus.OPEN);
	    issue.setSeverity(dto.getSeverity());
	    issue.setReporter(reporter);

	    if (dto.getComputerId() != null) {
	        // Issue tied to a specific computer
	        Computer computer = computerRepository.findById(dto.getComputerId())
	                .orElseThrow(() -> new ResourceNotFoundException("Computer not found"));
	        issue.setComputer(computer);
	        issue.setLab(computer.getLab()); // derive lab from computer
	    } else {
	        // Issue tied to a lab only
	        issue.setComputer(null);
	        issue.setLab(
	            labRepository.findById(dto.getLabId())
	            .orElseThrow(() -> new ResourceNotFoundException("Lab not found"))
	        );
	    }

	    Issue savedIssue = issueRepository.save(issue);
	    return mapToIssueResponseDTO(savedIssue);
	}
	
	public IssueResponseDTO startWork(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if(issue.getStatus() == IssueStatus.OPEN) {
			issue.setStatus(IssueStatus.IN_PROGRESS);
			Issue savedIssue = issueRepository.save(issue);
			IssueResponseDTO dto = mapToIssueResponseDTO(savedIssue);
			return dto;
		}
		throw new IllegalTransitionException(
			    "Invalid transition: Attempted to START WORK, but issue is currently " 
			    + issue.getStatus() 
			    + ". Allowed only from [OPEN]."
			);
	}
	
	public IssueResponseDTO escalateToFaculty(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if(issue.getStatus() == IssueStatus.OPEN || issue.getStatus() == IssueStatus.IN_PROGRESS) {
			issue.setStatus(IssueStatus.ESCALATED_TO_FACULTY);
			Issue savedIssue = issueRepository.save(issue);
			IssueResponseDTO dto = mapToIssueResponseDTO(savedIssue);
			return dto;
		}
		throw new IllegalTransitionException(
			    "Invalid transition: Attempted to ESCALATE THE ISSUE TO FACULTY, but issue is currently " 
			    + issue.getStatus() 
			    + ". Allowed only from [OPEN] or [IN_PROGRESS]."
			);
	}
	
	public IssueResponseDTO escalateToHod(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if(issue.getStatus() == IssueStatus.ESCALATED_TO_FACULTY) {
			issue.setStatus(IssueStatus.ESCALATED_TO_HOD);
			Issue savedIssue = issueRepository.save(issue);
			IssueResponseDTO dto = mapToIssueResponseDTO(savedIssue);
			return dto;
		}
		throw new IllegalTransitionException(
			    "Invalid transition: Attempted to ESCALATE THE ISSUE TO HOD, but issue is currently " 
			    + issue.getStatus() 
			    + ". Allowed only from [ESCALATED_TO_FACULTY]."
			);
	}
	
	public IssueResponseDTO resolveIssue(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if(issue.getStatus() == IssueStatus.IN_PROGRESS ||
				issue.getStatus() == IssueStatus.ESCALATED_TO_FACULTY ||
				issue.getStatus() == IssueStatus.ESCALATED_TO_HOD) {
			issue.setStatus(IssueStatus.RESOLVED);
			Issue savedIssue = issueRepository.save(issue);
			IssueResponseDTO dto = mapToIssueResponseDTO(savedIssue);
			return dto;
		}
		throw new IllegalTransitionException(
			    "Invalid transition: Attempted to  RESOLVE ISSUE, but issue is currently " 
			    + issue.getStatus() 
			    + ". Allowed only from [IN_PROGRESS] or [ESCALATED_TO_FACULTY] or [ESCALATED_TO_HOD]."
			);
	}

	public IssueResponseDTO closeIssue(Long issueId) {
		Issue issue = issueRepository.findById(issueId)
				.orElseThrow(() -> new ResourceNotFoundException("Issue not found"));
		if(issue.getStatus() == IssueStatus.RESOLVED) {
			issue.setStatus(IssueStatus.CLOSED);
			Issue savedIssue = issueRepository.save(issue);
			IssueResponseDTO dto = mapToIssueResponseDTO(savedIssue);
			return dto;
		}
		throw new IllegalTransitionException(
			    "Invalid transition: Attempted to CLOSE ISSUE, but issue is currently " 
			    + issue.getStatus() 
			    + ". Allowed only from [RESOLVED]."
			);
	}
	
	private IssueResponseDTO mapToIssueResponseDTO(Issue issue) {
		IssueResponseDTO dto = new IssueResponseDTO();
		
		dto.setId(issue.getId());
		dto.setTitle(issue.getTitle());
		dto.setDescription(issue.getDescription());
		dto.setSeverity(issue.getSeverity());
		dto.setStatus(issue.getStatus());
		dto.setReporterId(issue.getReporter().getId());
		dto.setReporterName(issue.getReporter().getName());
		dto.setLabId(issue.getLab().getId());
		dto.setRoomNumber(issue.getLab().getRoomNumber());
		
		if(issue.getComputer() != null) {
			dto.setComputerId(issue.getComputer().getId());
			dto.setComputerTag(issue.getComputer().getTag());
		} else {
			dto.setComputerId(null);
			dto.setComputerTag(null);
		}
		
		return dto;
	}
}
