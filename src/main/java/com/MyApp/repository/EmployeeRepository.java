package com.MyApp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MyApp.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository <Employee, Long> {
	Employee findByEmail(String email);
	
}
