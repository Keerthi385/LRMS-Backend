package com.lrms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lrms.entity.Booking;
import com.lrms.enums.BookingStatus;

public interface BookingRepository extends JpaRepository<Booking, Long>{
	List<Booking> findByLabIdAndStatus(Long labId, BookingStatus status);
	List<Booking> findByStatus(BookingStatus status);
	long countByStatus(BookingStatus status);


}
