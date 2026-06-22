package com.lrms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrms.entity.Lab;

public interface LabRepository extends JpaRepository<Lab, Long>{
	boolean existsByRoomNumber(Long roomNumber);
	
	Optional<Lab> findByRoomNumber(Long roomNumber);
	
}
