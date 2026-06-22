package com.lrms.dto;

import com.lrms.enums.IssueSeverity;
import com.lrms.enums.IssueStatus;

public class IssueResponseDTO {
	private Long id;

	private String title;

	private String description;

	private IssueSeverity severity;

	private IssueStatus status;

	private Long reporterId;
	
	private String reporterName;

	private Long labId;

	private Long roomNumber;

	private Long computerId;

	private String computerTag;

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

	public IssueStatus getStatus() {
		return status;
	}

	public void setStatus(IssueStatus status) {
		this.status = status;
	}

	public Long getReporterId() {
		return reporterId;
	}

	public void setReporterId(Long reporterId) {
		this.reporterId = reporterId;
	}

	public String getReporterName() {
		return reporterName;
	}

	public void setReporterName(String reporterName) {
		this.reporterName = reporterName;
	}

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Long getComputerId() {
		return computerId;
	}

	public void setComputerId(Long computerId) {
		this.computerId = computerId;
	}

	public String getComputerTag() {
		return computerTag;
	}

	public void setComputerTag(String computerTag) {
		this.computerTag = computerTag;
	}
	
	
}
