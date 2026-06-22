package com.lrms.dto;

import com.lrms.enums.ComputerStatus;

public class ComputerResponseDTO {
	private Long id;
	
	private String tag;
	
	private boolean audioReady;
	
	private boolean videoReady;
	
	private Integer nicSpeed;
	
	private ComputerStatus status;
	
	private Long LabId;
	
	private Long roomNumber;
	
	private String labName;

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
		return LabId;
	}

	public void setLabId(Long labId) {
		LabId = labId;
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
