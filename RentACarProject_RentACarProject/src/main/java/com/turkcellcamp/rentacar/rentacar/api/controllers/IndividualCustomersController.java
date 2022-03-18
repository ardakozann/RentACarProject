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
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.individualCustomerDtos.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualcustomers")
public class IndividualCustomersController {

	IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		return this.individualCustomerService.getAll();
	}
	
}
