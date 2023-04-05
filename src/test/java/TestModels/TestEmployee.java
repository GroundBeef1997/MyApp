package TestModels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import com.MyApp.MyAppApplication;
import com.MyApp.model.Car;
import com.MyApp.model.Employee;
import com.MyApp.model.Role;
import com.MyApp.service.CarService;
import com.MyApp.service.EmployeeService;

@SpringBootTest
@ContextConfiguration(classes = MyAppApplication.class)
class TestEmployee {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private CarService carService;
	
	@Test
	void addEmployee() {
		Employee employee = new Employee("jul", "bar", "jul@gmail.com", "password");    
	    employeeService.addEmployee(employee);
	    Employee e = employeeService.getEmployeeById(employee.getId());
	    assertEquals(e.getId(), employee.getId());
	    
	    Employee employee2 = new Employee("jul2", "bar2", "jul@gmail2.com", "password2"); 
	    employeeService.addEmployee(employee2);
	    
	    Employee e2 = employeeService.getEmployeeById(employee2.getId());
	    assertEquals(e2.getId(), employee2.getId());
	}
	
	@Test
	void updateEmployee() {
		Employee employeeUpdate = new Employee();
		employeeUpdate.setFirstName("julien2");
	    employeeService.addEmployee(employeeUpdate);
	    
		Employee employeeUpdated = new Employee();
		employeeUpdated.setFirstName("julien");
	    employeeService.update(employeeUpdate.getId(), employeeUpdated);
	    
	    Employee e1 = employeeService.getEmployeeById(employeeUpdate.getId());
	    assertEquals(e1.getFirstName(), employeeUpdated.getFirstName());
	}
	
	@Test
	void deleteEmployee() {
		Employee employee = new Employee();
		employee.setFirstName("julien3");	    
	    employeeService.addEmployee(employee);
	 
	    Car car = new Car();
	    car.setIntitule("volvo");
	    carService.addCarToEmployee(car, employee.getId());
	    
	    Car c2 = carService.getCarById(car.getId());
	    assertEquals(car.getId(), c2.getId());
	    
	    Map<String, Boolean> response = employeeService.deleteEmployee(employee.getId());
		Map<String, Boolean> response2 = new HashMap<>();
		response2.put("Employé n°" +employee.getId()+" à été supprimé", true);
		assertEquals(response, response2);
	    
	}
	
	@Test
	void addRoleToEmployee() {
		Employee employee = new Employee ("jul2", "bar2", "jul@gmail2.com", "password2");
		Role role2 = new Role();
		role2.setRoleName("EMPLOYEE");
		
		employeeService.addEmployee(employee);
		Employee e2 = employeeService.getEmployeeById(employee.getId());
	    employeeService.addRoleToEmployee(role2, employee.getId());
	    
	    assertEquals(e2.getId(), employee.getId());
	}
}
