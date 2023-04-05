package TestModels;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
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
class TestCar {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private CarService carService;

	@Test
	void addCar() {
		Employee employee2 = new Employee("jul2", "bar2", "jul@gmail2.com", "password2");
		employeeService.addEmployee(employee2);

		Role role2 = new Role();
		role2.setRoleName("EMPLOYEE");
		employeeService.addRoleToEmployee(role2, employee2.getId());

		Car car3 = new Car();
		car3.setIntitule("BENZ");

		Employee e = carService.addCarToEmployee(car3, employee2.getId());

		Car car = carService.getCarById(car3.getId());
		assertEquals(car.getId(), e.getCars().get(0).getId());
	}

	@Test
	void deleteCar() {
		Employee employee4 = new Employee("jul2", "bar2", "jul@gmail2.com", "password2");
		employeeService.addEmployee(employee4);

		Car car3 = new Car();
		car3.setIntitule("BENZ");

		carService.addCarToEmployee(car3, employee4.getId());
		
		Map<String, Boolean> response = carService.deleteCarFromEmployee(car3.getId());
		Map<String, Boolean> response2 = new HashMap<>();
		response2.put("Voiture n°" +car3.getId()+" à été supprimé", true);
		assertEquals(response, response2);
	}
	
	@Test
	void modifieCar() {
		Employee employee3 = new Employee("jul2", "bar2", "jul@gmail2.com", "password2");
		employeeService.addEmployee(employee3);

		Car car3 = new Car();
		car3.setIntitule("BENZ");

		Car car4 = new Car();
		car4.setIntitule("BENZ2");

		carService.addCarToEmployee(car3, employee3.getId());
		Car car = carService.updateCarFromEmployee(car3.getId(), car4);
		Employee e = car.getEmployee();
		assertEquals(car.getIntitule(), e.getCars().get(0).getIntitule());
	}
}
