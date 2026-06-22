package com.lrms.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lrms.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
	private final DashboardService dashboardService;
	
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/lab-utilization")
	public Map<String, Long> getLabUtilization(){
		return dashboardService.getLabUtilization();
	}
	
	@GetMapping("/issues")
	public Map<String, Long> getIssueStatistics() {
		return dashboardService.getIssueStatistics();
	}
	
	@GetMapping("/recruitment-readiness")
	public Map<String, Long> getRecuitmentReadiness() {
		return dashboardService.getRecruitmentReadiness();
	}
	
	@GetMapping("/bookings")
	public Map<String, Long> getBookingStatistics() {
		return dashboardService.getBookingStatistics();
	}
}
