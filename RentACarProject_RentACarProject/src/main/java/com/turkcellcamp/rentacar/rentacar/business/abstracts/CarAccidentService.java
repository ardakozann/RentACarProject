package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.GetCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.ListCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.DeleteCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CarAccidentService {
	Result add(CreateCarAccidentRequest createCarAccidentRequest);
	Result update(UpdateCarAccidentRequest updateCarAccidentRequest);
	Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest);
	DataResult <List<ListCarAccidentDto>> getAll();
	DataResult <List<ListCarAccidentDto>> getCarAccidentsByCarId(int carId);
	DataResult <GetCarAccidentDto> getCarAccidentsById(int carAccidentId);
}