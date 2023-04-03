package com.MyApp;

import org.modelmapper.ModelMapper;
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
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MyAppApplication.class, args);
	}
	
	@Bean 
	CommandLineRunner start (EmployeeService employeeService, CarService carService) {
		
		return args -> {
		/*	Role role = new Role();
			role.setRoleName("ADMIN");
			
			Role role2 = new Role();
			role2.setRoleName("EMPLOYEE");
			
			Employee employee = new Employee (0, "jul", "bar", "jul@gmail.com", "password", null, null);
			employeeService.addEmployee(employee);
			
			employeeService.addRoleToEmployee(role, employee.getId());
			employeeService.addRoleToEmployee(role2, employee.getId());
			
			Car car = new Car();
			car.setIntitule("citroen");
			
			Car car2 = new Car();
			car2.setIntitule("BMW");
			
			carService.addCarToEmployee(car, employee.getId());
			carService.addCarToEmployee(car2, employee.getId());
			
			
			 ----------- 2nd 
			
			Employee employee2 = new Employee (1, "jul2", "bar2", "jul@gmail2.com", "password2", null, null);
			employeeService.addEmployee(employee2);
			
			employeeService.addRoleToEmployee(role2, employee2.getId());
			
			Car car3 = new Car();
			car3.setIntitule("BENZ");
			
			carService.addCarToEmployee(car3, employee2.getId()); */
		};
		
	}
	
}
