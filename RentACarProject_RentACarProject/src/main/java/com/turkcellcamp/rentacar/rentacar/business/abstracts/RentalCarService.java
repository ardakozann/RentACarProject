package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.ListRentalCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface RentalCarService {

	Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException;
	Result update(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException;
	Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException;
	DataResult <List<ListRentalCarDto>> getAll();
	DataResult <List<GetRentalCarByCarIdDto>> getRentalCarByCarId(int carId) throws BusinessException;
	DataResult<GetRentalCarByIdDto> getRentalCarById(int rentalCarId) throws BusinessException;
	public boolean checkIfCarNotInRent(int carId) throws BusinessException;
	public boolean checkIfExistById(int id) throws BusinessException;
	public void totalPriceCalculateAfterAddAdditionalService(int rentalCarId);
}
