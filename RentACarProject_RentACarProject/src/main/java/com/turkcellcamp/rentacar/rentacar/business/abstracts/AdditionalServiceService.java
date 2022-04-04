package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.AdditionalService;

public interface AdditionalServiceService {
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
	Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
	DataResult <List<ListAdditionalServiceDto>> getAll();
	DataResult <GetAdditionalServiceByIdDto> getById(int additionalServiceId) throws BusinessException;
	public boolean checkIfExistById(int additionalServiceId) throws BusinessException;
	public AdditionalService getByIdForOtherService(int additionalServiceId);
}