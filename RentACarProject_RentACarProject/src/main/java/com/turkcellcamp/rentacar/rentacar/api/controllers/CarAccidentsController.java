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

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarAccidentService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.GetCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.ListCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenancesInCarDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.DeleteCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carAccidents")
public class CarAccidentsController {
	
	private CarAccidentService carAccidentService;

	public CarAccidentsController(CarAccidentService carAccidentService) {
		this.carAccidentService = carAccidentService;
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCarAccidentRequest createCarAccidentRequest) {
		return this.carAccidentService.add(createCarAccidentRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCarAccidentRequest updateCarAccidentRequest) {
		return this.carAccidentService.update(updateCarAccidentRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCarAccidentRequest deleteCarAccidentRequest) {
		return this.carAccidentService.delete(deleteCarAccidentRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCarAccidentDto>> getAll() {
		return this.carAccidentService.getAll();
	}
	
	@GetMapping("/getcaraccidentbyid")
	public DataResult<GetCarAccidentDto> getById(@RequestParam @Valid int carAccidentId) {
		return this.carAccidentService.getCarAccidentsById(carAccidentId);
	}
	
	@GetMapping("/getcaraccidentsbycarid")
	public DataResult<List<ListCarAccidentDto>> getCarAccidentsByCarId(@RequestParam @Valid int carId) throws BusinessException {
		return this.carAccidentService.getCarAccidentsByCarId(carId);
	}
	
}
