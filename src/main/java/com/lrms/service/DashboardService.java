package com.lrms.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.lrms.entity.Booking;
import com.lrms.entity.Lab;
import com.lrms.enums.BookingStatus;
import com.lrms.enums.IssueStatus;
import com.lrms.repository.BookingRepository;
import com.lrms.repository.IssueRepository;
import com.lrms.repository.LabRepository;

@Service
public class DashboardService {
	private final LabRepository labRepository;
	private final BookingRepository bookingRepository;
	private final IssueRepository issueRepository;
	
	public DashboardService(LabRepository labRepository,
			BookingRepository bookingRepository,
			IssueRepository issueRepository) {
		this.labRepository = labRepository;
		this.bookingRepository = bookingRepository;
		this.issueRepository = issueRepository;
	}
	
	public Map<String, Long> getLabUtilization() {
		Map<String, Long> response = new HashMap<>();
		long totalLabs = labRepository.count();
		LocalDateTime now = LocalDateTime.now();
		// Only consider approved bookings that are still ongoing or upcoming
	    List<Booking> activeBookings = bookingRepository.findByStatus(BookingStatus.APPROVED)
	        .stream()
	        .filter(b -> b.getEndTime().isAfter(now))
	        .toList();

	    // Collect distinct lab IDs from active bookings
	    Set<Long> bookedLabIds = activeBookings.stream()
	        .map(b -> b.getLab().getId())
	        .collect(Collectors.toSet());

	    long bookedLabs = bookedLabIds.size();
	    long availableLabs = totalLabs - bookedLabs;
		response.put("totalLabs", totalLabs);
		response.put("bookedLabs", bookedLabs);
		response.put("availableLabs", availableLabs);
		return response;
	}
	
	public Map<String, Long> getIssueStatistics() {
		Map<String, Long> response = new HashMap<>();
		response.put("open",issueRepository.countByStatus(IssueStatus.OPEN));
		response.put("inProgress", issueRepository.countByStatus(IssueStatus.IN_PROGRESS));
		response.put("escalatedToFaculty", issueRepository.countByStatus(IssueStatus.ESCALATED_TO_FACULTY));
		response.put("escalatedToHod", issueRepository.countByStatus(IssueStatus.ESCALATED_TO_HOD));
	    response.put("resolved", issueRepository.countByStatus(IssueStatus.RESOLVED));
	    response.put("closed", issueRepository.countByStatus(IssueStatus.CLOSED));
		return response;
	}
	
	public Map<String, Long> getRecruitmentReadiness() {
		Map<String, Long> response = new HashMap<>();
		List<Lab> labs = labRepository.findAll();
		long readyLabs = 0;
		for(Lab lab : labs) {
			if(lab.isPowerBackup() && lab.isAvReady() && lab.getInternetBandwidth() >= 100)
				readyLabs++;
		}
		Long notReadyLabs = labs.size() - readyLabs;
		response.put("readyLabs", readyLabs);
		response.put("notReadyLabs", notReadyLabs);
		return response;
	}
	
	public Map<String, Long> getBookingStatistics() {
		Map<String, Long> response = new HashMap<>();
		response.put("requested", bookingRepository.countByStatus(BookingStatus.REQUESTED));
	    response.put("approved", bookingRepository.countByStatus(BookingStatus.APPROVED));
	    response.put("rejected", bookingRepository.countByStatus(BookingStatus.REJECTED));
		return response;
	}
}
