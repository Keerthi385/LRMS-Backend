package com.lrms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="recruitment_drives")
public class RecruitmentDrive {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private LocalDate date;
	
	private LocalTime startTime;
	
	private LocalTime endTime;
	
	private Integer expectedHeadCount;
	
	private Integer requiredBandwidth;
	
	private boolean requiresPowerBackup;
	
	private boolean requiresAV;
	
	@ManyToOne
	@JoinColumn(name="assigned_lab_id")
	private Lab assignedLab;
	
	@ManyToOne
	@JoinColumn(name="created_by")
	private User createdBy;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}
	
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

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

	public Lab getAssignedLab() {
		return assignedLab;
	}

	public void setAssignedLab(Lab assignedLab) {
		this.assignedLab = assignedLab;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
