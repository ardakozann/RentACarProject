package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.GetBrandByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.brandDtos.ListBrandDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.CreateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.DeleteBrandRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests.UpdateBrandRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.BrandDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Brand;
@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;
	
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
		
		checkIfNotExistByBrandName(createBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		return new SuccessResult(BusinessMessage.BRANDSERVICE_ADD);
	}
	
	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
		
		checkIfExistByBrandId(updateBrandRequest.getBrandId());
		checkIfNotExistByBrandName(updateBrandRequest.getBrandName());
		
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		
		this.brandDao.save(brand);
		
		return new SuccessResult(BusinessMessage.BRANDSERVICE_UPDATE);
	}
	
	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
		
		checkIfExistByBrandId(deleteBrandRequest.getBrandId());
		
		this.brandDao.deleteById(deleteBrandRequest.getBrandId());
		
		return new SuccessResult(BusinessMessage.BRANDSERVICE_DELETE);
	}
	
	@Override
	public DataResult <List<ListBrandDto>> getAll() {
		
		var result = this.brandDao.findAll();
		
		List<ListBrandDto> response = result.stream().map(brand->this.modelMapperService.forDto()
				.map(brand, ListBrandDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<ListBrandDto>>(response);
	}
		
	@Override
	public DataResult <GetBrandByIdDto> getById(int brandId) throws BusinessException {
		
		checkIfExistByBrandId(brandId);
		
		Brand result = this.brandDao.getByBrandId(brandId);
		GetBrandByIdDto response = this.modelMapperService.forDto().map(result, GetBrandByIdDto.class);
		
		return new SuccessDataResult<GetBrandByIdDto>(response, BusinessMessage.BRANDSERVICE_GETBYID);
	}
	
	
	private boolean checkIfNotExistByBrandName(String brandName) throws BusinessException {
		Brand brand = this.brandDao.getByBrandName(brandName);
		if(brand != null) {
			throw new BusinessException(BusinessMessage.BRANDSERVICE_CHECKIFEXISTBYNAME_ERROR);
		}
		else {
			return true;
		}
	}
	
	public boolean checkIfExistByBrandId(int brandId) throws BusinessException {
		if(this.brandDao.getByBrandId(brandId) == null) {
			throw new BusinessException(BusinessMessage.BRANDSERVICE_CHECKIFEXISTBYID_ERROR);
		}else {
			return true;
		}
	}

}