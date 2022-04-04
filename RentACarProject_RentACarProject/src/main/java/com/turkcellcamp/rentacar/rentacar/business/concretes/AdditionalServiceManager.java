package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.GetAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.additionalServiceDtos.ListAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.CreateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.DeleteAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.additionalServiceRequests.UpdateAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.AdditionalServiceDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.AdditionalService;
@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}
// kontrol fonksiyonlarını ekle
	
	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		checkIfNotExistByName(createAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(BusinessMessage.ADDITIONALSERVICE_ADD);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		
		checkIfExistById(updateAdditionalServiceRequest.getAdditionalServiceId());
		checkIfNotExistByName(updateAdditionalServiceRequest.getAdditionalServiceName());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(BusinessMessage.ADDITIONALSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		
		checkIfExistById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
		return new SuccessResult(BusinessMessage.ADDITIONALSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListAdditionalServiceDto>> getAll() {
		var result = this.additionalServiceDao.findAll();
		List<ListAdditionalServiceDto> response = result.stream().map(additionalService -> modelMapperService.forDto().
				map(additionalService, ListAdditionalServiceDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<GetAdditionalServiceByIdDto> getById(int additionalServiceId) throws BusinessException {
		
		checkIfExistById(additionalServiceId);
		
		AdditionalService result = additionalServiceDao.getById(additionalServiceId);
		GetAdditionalServiceByIdDto response = modelMapperService.forDto().map(result, GetAdditionalServiceByIdDto.class);
		
		return new SuccessDataResult<GetAdditionalServiceByIdDto>(response,BusinessMessage.ADDITIONALSERVICE_GETBYID);
	}
	
	
	public AdditionalService getByIdForOtherService(int additionalServiceId) {
		return additionalServiceDao.getById(additionalServiceId);
	}

	public boolean checkIfExistById(int additionalServiceId) throws BusinessException {
		var result = this.additionalServiceDao.getByAdditionalServiceId(additionalServiceId);
		if(result == null) {
			throw new BusinessException(BusinessMessage.ADDITIONALSERVICE_CHECKIFEXISTBYID_ERROR);
		}
		return true;
	}
	
	private boolean checkIfNotExistByName(String name) throws BusinessException {
		var result = this.additionalServiceDao.getByAdditionalServiceName(name);
		if(result != null) {
			throw new BusinessException(BusinessMessage.ADDITIONALSERVICE_CHECKIFEXISTBYNAME_ERROR);
		}
		return true;
	}
}