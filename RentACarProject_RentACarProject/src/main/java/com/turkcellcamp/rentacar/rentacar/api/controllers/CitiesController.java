package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CityService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cityDtos.ListCityDto;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;

@RestController
@RequestMapping("/api/cities")
public class CitiesController {

	CityService cityService;

	@Autowired
	public CitiesController(CityService cityService) {
		this.cityService = cityService;
	}
	
	@GetMapping("/getall")
	public DataResult <List<ListCityDto>> getAll() {
		return this.cityService.getAll();
	}
}
