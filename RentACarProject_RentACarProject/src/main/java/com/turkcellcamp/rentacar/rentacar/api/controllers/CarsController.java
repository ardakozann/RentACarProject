package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.ListCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.DeleteCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
	private CarService carService;

	@Autowired
	public CarsController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListCarDto>> getAll() {
		return this.carService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarRequest createCarRequest) throws BusinessException {
		return this.carService.add(createCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarRequest updateCarRequest) throws BusinessException{
		return this.carService.update(updateCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarRequest deleteCarRequest) throws BusinessException{
		return this.carService.delete(deleteCarRequest);
	}
	
	@GetMapping("/getbyid")
	public DataResult<GetCarByIdDto> getById(@RequestParam @Valid int carId) throws BusinessException {
		return this.carService.getById(carId);
	}
	
	@GetMapping("/getCarByDailyPrice")
	DataResult <List<GetCarByDailyPriceDto>> getCarByDailyPrice(@RequestParam @Valid double dailyPrice){
		return this.carService.getCarByDailyPrice(dailyPrice);
	}
	
	@GetMapping("/getAllPaged")
	DataResult <List<ListCarDto>> getAllPaged(@RequestParam @Valid int pageNumber, @RequestParam @Valid int pageSize){
		return this.carService.getAllPaged(pageNumber, pageSize);
	}
	
	@GetMapping("/getAllSorted")
	DataResult <List<ListCarDto>> getAllSorted(){
		return this.carService.getAllSorted();
	}
}
