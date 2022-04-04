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

import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.ListRentalCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentalcars")
public class RentalCarsController {
	private RentalCarService rentalCarService;

	public RentalCarsController(RentalCarService rentalCarService) {
		this.rentalCarService = rentalCarService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
		return this.rentalCarService.add(createRentalCarRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException{
		return this.rentalCarService.update(updateRentalCarRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException{
		return this.rentalCarService.delete(deleteRentalCarRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListRentalCarDto>> getAll() {
		return this.rentalCarService.getAll();
	}
	
	@GetMapping("/getrentalcarbycarid/{carId}")
	public DataResult<List<GetRentalCarByCarIdDto>> getRentalCarByCarId(@RequestParam("carId") @Valid int carId) throws BusinessException{
		return this.rentalCarService.getRentalCarByCarId(carId);
	}
	
	@GetMapping("/getrentalcarbyid/{rentalcarid}")
	public DataResult<GetRentalCarByIdDto> getRentalCarById(@RequestParam("rentalCarId") @Valid int rentalCarId) throws BusinessException{
		return this.rentalCarService.getRentalCarById(rentalCarId);
	}
}