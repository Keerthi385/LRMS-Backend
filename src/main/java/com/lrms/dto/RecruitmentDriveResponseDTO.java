package com.lrms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class RecruitmentDriveResponseDTO {

    private Long id;

    private String title;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer expectedHeadCount;

    private Integer requiredBandwidth;

    private boolean requiresPowerBackup;

    private boolean requiresAV;

    private Long assignedLabId;

    private Long roomNumber;

    private String labName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public Integer getExpectedHeadCount() {
		return expectedHeadCount;
	}

	public void setExpectedHeadCount(Integer expectedHeadCount) {
		this.expectedHeadCount = expectedHeadCount;
	}

	public Integer getRequiredBandwidth() {
		return requiredBandwidth;
	}

	public void setRequiredBandwidth(Integer requiredBandwidth) {
		this.requiredBandwidth = requiredBandwidth;
	}

	public boolean isRequiresPowerBackup() {
		return requiresPowerBackup;
	}

	public void setRequiresPowerBackup(boolean requiresPowerBackup) {
		this.requiresPowerBackup = requiresPowerBackup;
	}

	public boolean isRequiresAV() {
		return requiresAV;
	}

	public void setRequiresAV(boolean requiresAV) {
		this.requiresAV = requiresAV;
	}

	public Long getAssignedLabId() {
		return assignedLabId;
	}

	public void setAssignedLabId(Long assignedLabId) {
		this.assignedLabId = assignedLabId;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}
    
    
}