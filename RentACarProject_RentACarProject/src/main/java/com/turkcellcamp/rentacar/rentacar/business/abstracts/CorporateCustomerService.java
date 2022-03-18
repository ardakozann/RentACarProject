package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.corporateCustomerDtos.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException;
	DataResult <List<ListCorporateCustomerDto>> getAll();
}
