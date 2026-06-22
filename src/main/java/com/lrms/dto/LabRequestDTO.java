package com.lrms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LabRequestDTO {
	
	@NotNull(message = "Room Number cannot be empty")
	private Long roomNumber;
	
	@NotBlank(message = "Name cannot be empty")
	private String name;
	
	@NotBlank(message = "Location cannot be empty")
	private String location;
	
	@NotNull(message = "Capacity cannot be empty")
	private Integer capacity;
	
	private boolean powerBackup;
	
	@NotNull(message = "Internet Bandwidth cannot be empty")
	private Integer internetBandwidth;
	
	private boolean avReady;

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public boolean isPowerBackup() {
		return powerBackup;
	}

	public void setPowerBackup(boolean powerBackup) {
		this.powerBackup = powerBackup;
	}

	public Integer getInternetBandwidth() {
		return internetBandwidth;
	}

	public void setInternetBandwidth(Integer internetBandwidth) {
		this.internetBandwidth = internetBandwidth;
	}

	public boolean isAvReady() {
		return avReady;
	}

	public void setAvReady(boolean avReady) {
		this.avReady = avReady;
	}
	
	
}
