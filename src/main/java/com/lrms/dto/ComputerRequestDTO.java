package com.lrms.dto;

import com.lrms.enums.ComputerStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ComputerRequestDTO {
	
	@NotBlank(message = "Tag cannot be empty")
	private String tag;
	
	private boolean audioReady;
	
	private boolean videoReady;
	
	@NotNull(message = "NIC Speed cannot be empty")
	private Integer nicSpeed;
	
	@NotNull(message = "Status cannot be empty")
	private ComputerStatus status;
	
	@NotNull(message = "Lab ID cannot be empty")
	private Long labId;

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

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}
	
	
}
