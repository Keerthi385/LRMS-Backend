package com.lrms.entity;

import java.time.LocalDateTime;

import com.lrms.enums.ComputerStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "computers")
public class Computer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String tag;
	
	private boolean audioReady;
	
	private boolean videoReady;
	
	private Integer nicSpeed;
	
	@Enumerated(EnumType.STRING)
	private ComputerStatus status;
	
	@ManyToOne
	@JoinColumn(name = "lab_id", nullable = false)
	private Lab lab;
	
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isAudioReady() {
		return audioReady;
	}

	public void setAudioReady(boolean audioReady) {
		this.audioReady = audioReady;
	}

	public boolean isVideoReady() {
		return videoReady;
	}

	public void setVideoReady(boolean videoReady) {
		this.videoReady = videoReady;
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

	public Integer getNicSpeed() {
		return nicSpeed;
	}

	public void setNicSpeed(Integer nicSpeed) {
		this.nicSpeed = nicSpeed;
	}

	public ComputerStatus getStatus() {
		return status;
	}

	public void setStatus(ComputerStatus status) {
		this.status = status;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}
}
