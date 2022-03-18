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

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.corporateCustomerDtos.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporatecustomers")
public class CorporateCustomersController {

	CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException{
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException{
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		return this.corporateCustomerService.getAll();
	}
	
}
