package com.lrms.dto;

public class LabResponseDTO {
	private Long id;
	
	private Long roomNumber;
	
	private String name;
	
	private String location;
	
	private Integer capacity;
	
	private boolean powerBackup;
	
	private Integer internetBandwidth;
	
	private boolean avReady;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
