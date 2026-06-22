package com.lrms.dto;

import com.lrms.enums.IssueSeverity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class IssueRequestDTO {
	
	@NotBlank(message = "Title cannot be empty")
	private String title;

	@NotBlank(message = "Description cannot be empty")
	private String description;

	@NotNull(message = "Severity cannot be empty")
	private IssueSeverity severity;
	
	private Long reporterId;
	
	private Long labId;

	private Long computerId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public IssueSeverity getSeverity() {
		return severity;
	}

	public void setSeverity(IssueSeverity severity) {
		this.severity = severity;
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	public Long getComputerId() {
		return computerId;
	}

	public void setComputerId(Long computerId) {
		this.computerId = computerId;
	}
	
	
}
