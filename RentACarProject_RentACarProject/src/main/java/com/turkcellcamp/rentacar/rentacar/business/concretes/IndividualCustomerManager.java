package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.UserService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.individualCustomerDtos.ListIndividualCustomerDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.CreateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.DeleteIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.individualCustomerRequests.UpdateIndividualCustomerRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.IndividualCustomerDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;
	private UserService userService;
	
	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService,
			UserService userService) {
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
		this.userService = userService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		
		checkIfNotExistByEmail(createIndividualCustomerRequest.getEmail());
		checkIfNotExistByIdentityNumber(createIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest, IndividualCustomer.class);
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessage.INDIVIDUALCUSTOMERSERVICE_ADD);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
		
		checkIfNotExistByEmail(updateIndividualCustomerRequest.getEmail());
		checkIfNotExistByIdentityNumber(updateIndividualCustomerRequest.getIdentityNumber());
		
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomer.setUserId(this.individualCustomerDao.getByEmail(updateIndividualCustomerRequest.getEmail()).getUserId());
		
		this.individualCustomerDao.save(individualCustomer);
		
		return new SuccessResult(BusinessMessage.INDIVIDUALCUSTOMERSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
		
		checkIfExistByIdentityNuber(deleteIndividualCustomerRequest.getIdentityNumber());
		
		this.individualCustomerDao.delete(this.individualCustomerDao.getByIdentityNumber(deleteIndividualCustomerRequest.getIdentityNumber()));
		
		return new SuccessResult(BusinessMessage.INDIVIDUALCUSTOMERSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListIndividualCustomerDto>> getAll() {
		
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<ListIndividualCustomerDto> response = result.stream().map(individualCustomer->this.modelMapperService.forDto()
				.map(individualCustomer, ListIndividualCustomerDto.class)).collect(Collectors.toList());
		setIds(result, response);
		
		
		return new SuccessDataResult <List<ListIndividualCustomerDto>>(response);
	}

	
	private void setIds(List<IndividualCustomer> result, List<ListIndividualCustomerDto> response){
		for(int i = 0; i < result.size(); i++) {
			response.get(i).setId(result.get(i).getUserId());
		}
	}
	
	private boolean checkIfNotExistByEmail(String email) {
		this.userService.checkIfNotExistByEmail(email);
		return true;
	}
	
	private boolean checkIfNotExistByIdentityNumber(String identityNumber) {
		IndividualCustomer result = this.individualCustomerDao.getByIdentityNumber(identityNumber);
		if(result != null) {
			throw new BusinessException(BusinessMessage.INDIVIDUALCUSTOMERSERVICE_CHECKIFNOTEXISTBYIDENTITYNUMBER_ERROR);
		}
		return true;
	}
	
	private boolean checkIfExistByIdentityNuber(String identityNumber) {
		IndividualCustomer result = this.individualCustomerDao.getByIdentityNumber(identityNumber);
		if(result == null) {
			throw new BusinessException(BusinessMessage.INDIVIDUALCUSTOMERSERVICE_CHECKIFEXISTBYIDENTITYNUMBER_ERROR);
		}
		return true;
	}
}