package com.MyApp.controller;

import java.util.List;
import java.util.Map;
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

import com.MyApp.dto.CarDto;
import com.MyApp.model.Car;
import com.MyApp.service.CarService;
import org.modelmapper.ModelMapper;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/V1/")
public class CarController {
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	//permet d'obtenir toutes les voitures d'un employee.
	@GetMapping("employee/{id}/car")
	public List<Car> getAllCars(@PathVariable(value = "id") Long id) {
		return carService.getAllCarFromEmployee(id);
	}
	
	/*//Permet de mettre à jour une voiture.
	@PutMapping("employee/{idEmployee}/car/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long idEmployee, @PathVariable Long id, @RequestBody Car carDetails) {
		Car updatedCar = carService.updateCarFromEmployee(id, idEmployee, carDetails);
		return ResponseEntity.ok(updatedCar);
	}
	 * 
	 * */
	
	//Permet de retrouver une voiture selon son identifiant.
	@GetMapping("car/{id}")
	public ResponseEntity<Car> getCarById(@PathVariable Long id) {
		Car car = carService.getCarById(id);
		return ResponseEntity.ok(car);
	}
		
	//Permet d'ajouter une voiture à un employee.
	@PostMapping("employee/{id}/car")
	public Car addCar(@PathVariable(value = "id") Long id, @RequestBody CarDto carDto) {
		Car car = modelMapper.map(carDto, Car.class);
		return carService.addCarToEmployee(car,id);
	}
		
	//Permet de mettre à jour une voiture.
	@PutMapping("car/{id}")
	public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody CarDto carDetailsDto) {
		Car carDetails = modelMapper.map(carDetailsDto, Car.class);
		Car updatedCar = carService.updateCar(id, carDetails);
		return ResponseEntity.ok(updatedCar);
	}
		
	//Permet de supprimer une voiture.
	@DeleteMapping("car/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteCar(@PathVariable Long id) {	
		Map<String, Boolean> response = carService.deleteCar(id);
		return ResponseEntity.ok(response);
	}
}
