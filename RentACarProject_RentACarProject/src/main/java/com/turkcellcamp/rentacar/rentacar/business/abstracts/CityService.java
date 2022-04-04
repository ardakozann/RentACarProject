package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.cityDtos.ListCityDto;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.City;

public interface CityService {
	public DataResult<List<ListCityDto>> getAll();
	public City getByIdForOtherService(int id);
}