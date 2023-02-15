package com.MyApp;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.MyApp.model.Car;
import com.MyApp.model.Employee;
import com.MyApp.model.Role;
import com.MyApp.service.CarService;
import com.MyApp.service.EmployeeService;


@SpringBootApplication
public class MyAppApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}
	
	@Bean 
	CommandLineRunner start (EmployeeService employeeService, CarService carService) {
		
		return args -> {
			Role role = new Role();
			role.setRoleName("ADMIN");
			
			Role role2 = new Role();
			role2.setRoleName("EMPLOYEE");
			
			employeeService.addRole(role);
			employeeService.addRole(role2);
			
			Employee employee = new Employee (0, "jul", "bar", "jul@gmail.com", "password", null, null);
			employeeService.addEmployee(employee);
			
			employeeService.addRoleToEmployee(role.getRoleName(), employee.getEmail());
			employeeService.addRoleToEmployee(role2.getRoleName(), employee.getEmail());
			
			Car car = new Car();
			car.setIntitule("citroen");
			
			Car car2 = new Car();
			car2.setIntitule("BMW");
			
			employeeService.addCarToEmployee(car, employee.getId());
			employeeService.addCarToEmployee(car2, employee.getId());
		};
		
	}
	
}
