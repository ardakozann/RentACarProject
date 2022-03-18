package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenancesInCarDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
	private CarMaintenanceService carMaintenanceService;

	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService = carMaintenanceService;
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		return this.carMaintenanceService.add(createCarMaintenanceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException{
		return this.carMaintenanceService.delete(deleteCarMaintenanceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		return this.carMaintenanceService.getAll();
	}
	
	@GetMapping("/getcarmaintenancebyid")
	public DataResult<GetCarMaintenanceByIdDto> getById(@RequestParam @Valid int carMaintenanceId) throws BusinessException{
		return this.carMaintenanceService.getById(carMaintenanceId);
	}
	
	@GetMapping("/getcarmaintenancesbycarid")
	public DataResult<List<GetCarMaintenancesInCarDto>> getCarMaintenancesByCarId(@RequestParam @Valid int carId) throws BusinessException {
		return this.carMaintenanceService.getCarMaintenancesByCarId(carId);
	}
}
