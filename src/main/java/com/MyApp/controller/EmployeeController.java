package com.MyApp.controller;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.MyApp.dto.EmployeeDto;
import com.MyApp.dto.RoleDto;
import com.MyApp.model.Employee;
import com.MyApp.model.Role;
import com.MyApp.service.EmployeeService;

@RestController
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/V1")
public class EmployeeController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EmployeeService employeeService;

	// Permet de retrouver tout les employées
	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();
	}

	// Permet d'ajouter un employé
	@PostMapping("/employee")
	public Employee addEmployee(@RequestBody EmployeeDto employeeDto) {
		Employee employee = modelMapper.map(employeeDto, Employee.class);
		return employeeService.addEmployee(employee);
	}

	// Permet d'ajouter un role à un employee
	@PostMapping("/employee/{id}/role")
	public Employee addRoleToEmployee(@PathVariable Long id, @RequestBody RoleDto roleDto) {
		Employee employee = employeeService.getEmployeeById(id);
		Role role = modelMapper.map(roleDto, Role.class);
		return employeeService.addRoleToEmployee(role, employee.getId());
	}

	// Permet de retrouver un employee selon son identifiant
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
		Employee employee = employeeService.getEmployeeById(id);
		return ResponseEntity.ok(employee);
	}

	// Permet de mettre à jour un employé
	@PutMapping("/employee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDto employeeDetailsDto) {
		Employee employee = modelMapper.map(employeeDetailsDto, Employee.class);
		Employee updatedEmployee = employeeService.update(id, employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	// Permet de supprimer un employé
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id) {
		Map<String, Boolean> response = employeeService.deleteEmployee(id);
		return ResponseEntity.ok(response);
	}
	
	// Permet de retrouver tout les employées
	@GetMapping("/403")
	public String accesDeniedPage() {
		return "Acces denied ! unauthorized acces to this ressource.";
	}
	
	@GetMapping("/hello")
	public String getTest() {
		return "hello";
	}
}
