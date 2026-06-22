package com.lrms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "labs")
public class Lab {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private Long roomNumber;
	
	private String name;
	
	private String location;
	
	private Integer capacity;
	
	private boolean powerBackup;
	
	private Integer internetBandwidth;
	
	private boolean avReady;
	
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
	
	

	public Long getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(Long roomnumber) {
		this.roomNumber = roomnumber;
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
