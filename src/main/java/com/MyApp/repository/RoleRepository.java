package com.MyApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MyApp.model.Employee;
import com.MyApp.model.Role;



@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {
	Role findByRoleName(String roleName);
}