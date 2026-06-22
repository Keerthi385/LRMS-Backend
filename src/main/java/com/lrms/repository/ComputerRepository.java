package com.lrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrms.entity.Computer;

public interface ComputerRepository extends JpaRepository<Computer, Long> {
	List<Computer> findByLabId(Long labId);

	boolean existsByTag(String tag);
}
