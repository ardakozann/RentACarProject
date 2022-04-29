package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
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

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	
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
		
		return new SuccessResult(BusinessMessage.CORPORATECUSTOMERSERVICE_ADD);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
		
		checkIfNotExistByEmail(updateCorporateCustomerRequest.getEmail());
		checkIfNotExistByTaxNumber(updateCorporateCustomerRequest.getTaxNumber());
		
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest, CorporateCustomer.class);
		corporateCustomer.setUserId(this.corporateCustomerDao.getByTaxNumber(updateCorporateCustomerRequest.getTaxNumber()).getUserId());
		
		this.corporateCustomerDao.save(corporateCustomer);
		
		return new SuccessResult(BusinessMessage.CORPORATECUSTOMERSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
		
		checkIfExistByTaxNumber(deleteCorporateCustomerRequest.getTaxNumber());
		
		this.corporateCustomerDao.delete(this.corporateCustomerDao.getByTaxNumber(deleteCorporateCustomerRequest.getTaxNumber()));
		
		return new SuccessResult(BusinessMessage.CORPORATECUSTOMERSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListCorporateCustomerDto>> getAll() {
		
		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
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
		CorporateCustomer result = this.corporateCustomerDao.getByTaxNumber(taxNumber);
		if(result != null) {
			throw new BusinessException(BusinessMessage.CORPORATECUSTOMERSERVICE_CHECKIFNOTEXISTBYTAXNUMBER_ERROR);
		}
		return true;
	}
	
	private boolean checkIfExistByTaxNumber(String taxNumber) {
		CorporateCustomer result = this.corporateCustomerDao.getByTaxNumber(taxNumber);
		if(result == null) {
			throw new BusinessException(BusinessMessage.CORPORATECUSTOMERSERVICE_CHECKIFEXISTBYTAXNUMBER_ERROR);
		}
		return true;
	}
}