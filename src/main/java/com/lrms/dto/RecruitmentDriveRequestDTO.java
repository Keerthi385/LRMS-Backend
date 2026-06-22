package com.lrms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RecruitmentDriveRequestDTO {

    @NotBlank
    private String title;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;

    @NotNull
    private Integer expectedHeadCount;

    @NotNull
    private Integer requiredBandwidth;

    private boolean requiresPowerBackup;

    private boolean requiresAV;

    private Long createdById;

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

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}
    
    
}