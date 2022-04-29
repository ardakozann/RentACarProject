package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenancesInCarDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {
	DataResult<List<ListCarMaintenanceDto>> getAll();
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
	Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
	DataResult<List<GetCarMaintenancesInCarDto>> getCarMaintenancesByCarId(int carId);
	public DataResult<GetCarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException;
	boolean checkIfCarNotInMaintenance(int carId) throws BusinessException;
}