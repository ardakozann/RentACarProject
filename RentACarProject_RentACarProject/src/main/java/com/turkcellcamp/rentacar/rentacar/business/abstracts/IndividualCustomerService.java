package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.individualCustomerDtos.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface IndividualCustomerService {
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException;
	DataResult <List<ListIndividualCustomerDto>> getAll();
}
