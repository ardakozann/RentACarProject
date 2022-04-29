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

import com.turkcellcamp.rentacar.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.GetBrandByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.ListBrandDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.CreateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
	private BrandService brandService;

	@Autowired
	public BrandsController(BrandService brandService) {
		this.brandService = brandService;
	}

	@GetMapping("/getall")
	public DataResult<List<ListBrandDto>> getAll() {
		return this.brandService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws BusinessException {
		return this.brandService.add(createBrandRequest);
	}
	
	@PutMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws BusinessException{
		return this.brandService.update(updateBrandRequest);
	}
	
	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) throws BusinessException{
		return this.brandService.delete(deleteBrandRequest);
	}
	
	@GetMapping("/getbyid")
	public DataResult<GetBrandByIdDto> getById(@RequestParam @Valid int brandId) throws BusinessException {
		return this.brandService.getById(brandId);
	}
}
