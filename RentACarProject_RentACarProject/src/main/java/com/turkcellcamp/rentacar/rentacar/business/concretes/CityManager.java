package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CityService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.cityDtos.ListCityDto;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CityDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.City;

@Service
public class CityManager implements CityService {

	CityDao cityDao;
	ModelMapperService modelMapperService;
	
	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ListCityDto>> getAll() {
		
		var result = this.cityDao.findAll();
		List<ListCityDto> response = result.stream().map(city -> this.modelMapperService.forDto().
				map(city,ListCityDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCityDto>>(response);
	}

	@Override
	public City getByIdForOtherService(int id) {
		return cityDao.getByCityId(id);
	}

}
