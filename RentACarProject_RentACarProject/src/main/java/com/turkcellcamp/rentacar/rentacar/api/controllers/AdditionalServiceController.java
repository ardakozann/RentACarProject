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

import com.turkcellcamp.rentacar.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalservices")
public class AdditionalServiceController {
	
	private AdditionalServiceService additionalServiceService;
	
	@Autowired
	public AdditionalServiceController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		return this.additionalServiceService.add(createAdditionalServiceRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException{
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException{
		return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		return this.additionalServiceService.getAll();
	}
	
	@GetMapping("/getadditionalServicebyid")
	public DataResult<GetAdditionalServiceByIdDto> GetAdditionalServiceById(@RequestParam @Valid int additionalServiceId) throws BusinessException {
		return this.additionalServiceService.getById(additionalServiceId);
	}
	
}
