package com.MyApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MyApp.model.Car;

@Repository
public interface CarRepository extends JpaRepository <Car, Long> {
	List<Car> findByEmployeeId(Long id);
}
