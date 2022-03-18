package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.ListCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.DeleteCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CarService {
	DataResult <List<ListCarDto>> getAll();
	Result add(CreateCarRequest createCarRequest) throws BusinessException;
	DataResult <GetCarByIdDto> getById(int carId) throws BusinessException;
	Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
	Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
	DataResult <List<GetCarByDailyPriceDto>> getCarByDailyPrice(double dailyPrice);
	DataResult <List<ListCarDto>> getAllPaged(int pageNumber, int pageSize);
	DataResult <List<ListCarDto>> getAllSorted();
	boolean checkIfExistByCarId(int carId) throws BusinessException;
	void toSetCarOdometer(int carId, long odometer);
}
