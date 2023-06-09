package com.MyApp.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MyApp.exception.ResourceNotFoundException;
import com.MyApp.model.Employee;
import com.MyApp.model.Role;
import com.MyApp.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	static final String MESSAGE_CAR = "La voiture que vous recherchez n'existe pas";
	static final String MESSAGE_EMPLOYEE = "l'employé que vous recherchez n'existe pas";

	/**
	 * ------------------ gestion des roles d'un employee
	 * ------------------------------------
	 **/

	public Employee addRoleToEmployee(Role role, Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));

		if (!role.getRoleName().equals("ADMIN") && !role.getRoleName().equals("EMPLOYEE")) {
			throw new ResourceNotFoundException("Role is invalid ! " + role.getRoleName());
		}

		for (Role r : employee.getRoles()) {
			if (role.getRoleName().equals(r.getRoleName())) {
				throw new ResourceNotFoundException("Role is existant ! " + role.getRoleName());
			}
		}

		employee.getRoles().add(role);
		return employeeRepository.save(employee);
	}

	/**
	 * ------------------ gestion des employees ------------------------------------
	 **/
	// Permet de retrouver tout les employées
	public List<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// Permet d'ajouter un employé
	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	// Permet de retrouver un employee selon son identifiant
	public Employee getEmployeeById(Long id) {
		return employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));
	}

	// Permet de mettre à jour un employee
	public Employee update(Long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPassword(employeeDetails.getPassword());
		return employeeRepository.save(employee);
	}

	// Permet de supprimer un employé
	public Map<String, Boolean> deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));

		employeeRepository.delete(employee);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Employé n°" + id + " à été supprimé", Boolean.TRUE);
		return response;
	}

	/**
	 * ------------- gestion spring security aux employees
	 * -----------------------------------------------
	 **/

	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Override
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		final Employee employee = employeeRepository.findByEmail(mail);
		if (employee == null) {
			throw new UsernameNotFoundException(mail);
		}
		String [] roles = new String[employee.getRoles().size()];
		int i=0;
		for (Role r : employee.getRoles()) {
			roles[i] = r.getRoleName();
			i++;
		}
		return User.withUsername(employee.getEmail()).password((new BCryptPasswordEncoder().encode(employee.getPassword()))) 
				.roles(roles).build();
	}

}
