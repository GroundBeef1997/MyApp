package com.MyApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.MyApp.exception.ResourceNotFoundException;
import com.MyApp.model.Car;
import com.MyApp.model.Employee;
import com.MyApp.model.Role;
import com.MyApp.repository.CarRepository;
import com.MyApp.repository.EmployeeRepository;
import com.MyApp.repository.RoleRepository;

@Service
@Transactional
public class EmployeeService implements UserDetailsService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private CarRepository carRepository;

	/**------------------ gestion des roles d'un employee ------------------------------------  **/
	public Role addRole(Role role) {
		return roleRepository.save(role);
	}
	
	public void addRoleToEmployee (String roleName, String employeeMail) {
		try {
			Employee employee = employeeRepository.findByEmail(employeeMail);
			Role role = roleRepository.findByRoleName(roleName);
			employee.getRoles().add(role);
			employeeRepository.save(employee);
		} catch (ResourceNotFoundException e) {
			System.out.println("l'employé que vous recherchez n'existe pas ou alors le role est introuvable");
		}	
	}

	/**------------------ gestion des employees ------------------------------------  **/
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
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("l'employé que vous recherchez n'existe pas"));
		return employee;
	}

	// Permet de mettre à jour un employé
	public Employee update(Long id, Employee employeeDetails) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("l'employé que vous recherchez n'existe pas"));

		employee.setFirstName(employeeDetails.getFirstName());
		employee.setLastName(employeeDetails.getLastName());
		employee.setEmail(employeeDetails.getEmail());
		employee.setPassword(employeeDetails.getPassword());
		Employee UpdatedEmployee = employeeRepository.save(employee);

		return UpdatedEmployee;
	}

	// Permet de supprimer un employé
	public Map<String, Boolean> deleteEmployee(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("l'employé que vous recherchez n'existe pas"));

		employeeRepository.delete(employee);

		Map<String, Boolean> response = new HashMap<>();
		response.put("Employé n°" + id + " à été supprimé", Boolean.TRUE);
		return response;
	}
	
	/**------------- gestion des voitures des employees -----------------------------------------------**/
	
	//Permet de retrouver toutes voitures d'un employee.
		public List<Car> getAllCarFromEmployee(Long id) {
			return carRepository.findByEmployeeId(id);
		}
		
		//Permet d'ajouter une voiture.
		public Car addCarToEmployee(Car car, Long id) {
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("l'employee que vous recherchez n'existe pas"));
			
			car.setEmployee(employee);
			return carRepository.save(car);
		}
			
		//Permet de retrouver une voiture selon son identifiant.
		public Car getCarById(Long id) {
			Car car = carRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
			
			return car;
		}
			
		//Permet de mettre à jour une voiture.
		public Car updateCar(Long id, Car carDetails) {
			Car car = carRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
				
			car.setIntitule(carDetails.getIntitule());
			Car updatedCar = carRepository.save(car);
			return updatedCar;
		}
		
		//Permet de mettre à jour une voiture d'un employee.
		public Car updateCarFromEmployee(Long idCar, Long id,  Car carDetails) {
				
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("L'employee parent que vous recherchez n'existe pas"));
			
			Car car = carRepository.findById(idCar)
					.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
			
			List<Car> cars = new ArrayList<Car>();	
			car.setIntitule(carDetails.getIntitule());
			Car updatedCar = carRepository.save(car);
			
			employee.setCars(cars);
			employeeRepository.save(employee);
			
			return updatedCar;
			
		}
			
		//Permet de supprimer une voiture et renvoyer un message de confirmation.
		public Map<String, Boolean> deleteCar(Long id) {
			Car car = carRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
				
			carRepository.delete(car);
			Map<String, Boolean> response = new HashMap<>();
			response.put("Voiture n°" + id + " à été supprimé", Boolean.TRUE);
			return response;
		}

		/**------------- gestion spring security aux employees -----------------------------------------------**/
		
		PasswordEncoder encoder =
			     PasswordEncoderFactories.createDelegatingPasswordEncoder();
		@Override
	    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
	        final Employee employee = employeeRepository.findByEmail(mail);
	        if (employee == null) {
	            throw new UsernameNotFoundException(mail);
	        }
	        
	        UserDetails user = User.withUsername(employee.getEmail()).password((encoder.encode(employee.getPassword()))).authorities("ADMIN", "EMPLOYEE").build();
	        return user;
	    }
		
}
