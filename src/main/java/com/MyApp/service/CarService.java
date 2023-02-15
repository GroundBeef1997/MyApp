package com.MyApp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MyApp.exception.ResourceNotFoundException;
import com.MyApp.model.Car;
import com.MyApp.model.Employee;
import com.MyApp.repository.CarRepository;
import com.MyApp.repository.EmployeeRepository;

@Service
public class CarService {

	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//Permet de retrouver toutes les entreprises.
	public List<Car> getAllCar(Long id) {
		return carRepository.findByEmployeeId(id);
	}
	
	//Permet d'ajouter une entreprise.
	public Car addCar(Car car, Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("l'employee que vous recherchez n'existe pas"));
		
		car.setEmployee(employee);
		return carRepository.save(car);
	}
		
	//Permet de retrouver une entreprise selon son identifiant.
	public Car getCarById(Long id) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
		
		return car;
	}
		
	//Permet de mettre à jour une entreprise.
	public Car updateCar(Long id, Car carDetails) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
			
		car.setIntitule(carDetails.getIntitule());
		Car updatedCar = carRepository.save(car);
		return updatedCar;
	}
	
	//Permet de retourner un employee depuis une voiture.
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
		
	//Permet de supprimer une entreprise.
	public Map<String, Boolean> deleteCar(Long id) {
		Car car = carRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("La voiture que vous recherchez n'existe pas"));
			
		carRepository.delete(car);
		Map<String, Boolean> response = new HashMap<>();
		response.put("Voiture n°" + id + " à été supprimé", Boolean.TRUE);
		return response;
	}
		
}
