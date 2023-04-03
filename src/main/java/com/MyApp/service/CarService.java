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

	static final String MESSAGE_CAR = "La voiture que vous recherchez n'existe pas";
	static final String MESSAGE_EMPLOYEE = "l'employé que vous recherchez n'existe pas";

	// Permet de retrouver toutes voitures d'un employee.
	public List<Car> getAllCarFromEmployee(Long id) {
		return carRepository.findByEmployeeId(id);
	}

	// Permet d'ajouter une voiture.
	public Employee addCarToEmployee(Car car, Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));
		employee.getCars().add(car);
		car.setEmployee(employee);
		carRepository.save(car);
		return employeeRepository.save(employee);
	}

	// Permet de retrouver une voiture selon son identifiant.
	public Car getCarById(Long id) {
		return carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MESSAGE_CAR));
	}

	// Permet de mettre à jour une voiture.
	public Car updateCar(Long id, Car carDetails) {
		Car car = carRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(MESSAGE_CAR));

		car.setIntitule(carDetails.getIntitule());
		return carRepository.save(car);
	}

	// Permet de mettre à jour une voiture d'un employee.
	public Car updateCarFromEmployee(Long idCar, Car carDetails) {		
		Car car = carRepository.findById(idCar).orElseThrow(() -> new ResourceNotFoundException(MESSAGE_CAR));
		Employee employee = employeeRepository.findById(car.getEmployee().getId())
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));

		car.setIntitule(carDetails.getIntitule());
		carRepository.save(car);
		
		List<Car> cars = employee.getCars();
		for (Car c : cars) {
			if (c.getId() == car.getId()) {
				cars.remove(c);
				cars.add(car);
			}
		}
		employee.setCars(cars);			
		employeeRepository.save(employee);
		return carRepository.save(car);
	}

	// Permet de supprimer une voiture et renvoyer un message de confirmation.
	public Map<String, Boolean> deleteCarFromEmployee(Long idCar) {
		Car car = carRepository.findById(idCar).orElseThrow(() -> new ResourceNotFoundException(MESSAGE_CAR));
		
		Employee employee = employeeRepository.findById(car.getEmployee().getId())
				.orElseThrow(() -> new ResourceNotFoundException(MESSAGE_EMPLOYEE));
		
		employee.getCars().remove(car);
		employeeRepository.save(employee);
		carRepository.delete(car);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("Voiture n°" + idCar + " à été supprimé", Boolean.TRUE);
		return response;
	}
}
