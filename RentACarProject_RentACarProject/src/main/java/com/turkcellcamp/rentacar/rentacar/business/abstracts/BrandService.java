package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.GetBrandByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.ListBrandDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.CreateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface BrandService {
	
	Result add(CreateBrandRequest createBrandRequest) throws BusinessException;
	Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;
	Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException;
	DataResult <List<ListBrandDto>> getAll();
	DataResult <GetBrandByIdDto> getById(int brandId) throws BusinessException;
	boolean checkIfExistByBrandId(int brandId) throws BusinessException;
}