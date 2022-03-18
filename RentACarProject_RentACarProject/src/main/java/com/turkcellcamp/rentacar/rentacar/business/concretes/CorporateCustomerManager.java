package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.corporateCustomerDtos.ListCorporateCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.CreateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.DeleteCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.corporateCustomerRequests.UpdateCorporateCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CorporateCustomerDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	CorporateCustomerDao corporateCustomerDao;
	ModelMapperService modelMapperService;
	UserService userService;
	
	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
		
		checkIfNotExistByEmail(createCorporateCustomerRequest.getEmail());
		checkIfNotExistByTaxNumber(createCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest, CorporateCustomer.class);
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Added");
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfNotExistByEmail(updateCorporateCustomerRequest.getEmail());
		checkIfNotExistByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(this.corporateCustomerDao.getByEmail(updateCorporateCustomerRequest.getEmail()).getUserId());
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult("CorporateCustomer.Update");
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfExistByEmail(deleteCorporateCustomerRequest.getEmail());
		
		this.corporateCustomerDao.delete(this.corporateCustomerDao.getByEmail(deleteCorporateCustomerRequest.getEmail()));
		
		return new SuccessResult("CorporateCustomer.Deleted");
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		var result = this.corporateCustomerDao.findAll();
		List<ListCorporateCustomerDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto()
				.map(corporateCustomer, ListCorporateCustomerDto.class)).collect(Collectors.toList());
		setIds(result, response);
		
		return new SuccessDataResult <List<ListCorporateCustomerDto>>(response);
	}

	//getall fonksiyonu için set etme fonksiyonu(modelmapper hatası)
	private void setIds(List<CorporateCustomer> result, List<ListCorporateCustomerDto> response){
		for(int i = 0; i < result.size(); i++) {
			response.get(i).setId(result.get(i).getUserId());
		}
	}
	
	private boolean checkIfNotExistByEmail(String email) {
		this.userService.checkIfNotExistByEmail(email);
		return true;
	}
	
	private boolean checkIfNotExistByTaxNumber(String taxNumber) {
		var result = this.corporateCustomerDao.getByTaxNumber(taxNumber);
		if(result != null) {
			throw new BusinessException("Tax Number already exist.");
		}
		return true;
	}
	
	private boolean checkIfExistByEmail(String email) {
		var result = this.corporateCustomerDao.getByEmail(email);
		if(result == null) {
			throw new BusinessException("Can not find Corporate Customer with this id.");
		}
		return true;
	}
}
