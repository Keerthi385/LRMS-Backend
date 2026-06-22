package com.lrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lrms.entity.Issue;
import com.lrms.enums.IssueStatus;

public interface IssueRepository extends JpaRepository<Issue, Long> {
	
	List<Issue> findByStatus(IssueStatus status);
	
	List<Issue> findByLabId(Long labId);
	
	List<Issue> findByComputerId(Long computerId);
	
	long countByStatus(IssueStatus status);
}
