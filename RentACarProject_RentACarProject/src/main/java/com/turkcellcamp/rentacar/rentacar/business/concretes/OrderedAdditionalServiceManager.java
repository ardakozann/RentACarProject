package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByRentalCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;
@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	ModelMapperService modelMapperService;
	OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	AdditionalServiceService additionalServiceService;
	RentalCarService rentalCarService;
	
	@Autowired
	public OrderedAdditionalServiceManager(ModelMapperService modelMapperService,
			OrderedAdditionalServiceDao orderedAdditionalServiceDao, AdditionalServiceService additionalServiceService,
			RentalCarService rentalCarService) {
		this.modelMapperService = modelMapperService;
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.additionalServiceService = additionalServiceService;
		this.rentalCarService = rentalCarService;
	}


	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfRentalCarExistByRentalCarId(createOrderedAdditionalServiceRequest.getRentalCarId());
		checkIfAdditionalServiceExistByAdditionalServiceId(createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInCarInRent(createOrderedAdditionalServiceRequest.getRentalCarId(),
				createOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest,
				OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		
		return new SuccessResult("OrderedAdditionalService.Added");
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfRentalCarExistByRentalCarId(updateOrderedAdditionalServiceRequest.getRentalCarId());
		checkIfAdditionalServiceExistByAdditionalServiceId(updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfNotExistAdditionalServiceInCarInRent(updateOrderedAdditionalServiceRequest.getRentalCarId(),
				updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		checkIfExistAdditionalServiceInCarInRent(updateOrderedAdditionalServiceRequest.getRentalCarId(),
				updateOrderedAdditionalServiceRequest.getNewAdditionalServiceId());
		
		var result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarIdAndAdditionalService_additionalServiceId(updateOrderedAdditionalServiceRequest.getRentalCarId(),
				updateOrderedAdditionalServiceRequest.getAdditionalServiceId());
		OrderedAdditionalService response = this.modelMapperService.forRequest().map(result, OrderedAdditionalService.class);
		response.setAdditionalService(this.additionalServiceService.getByIdForOtherService(updateOrderedAdditionalServiceRequest.getNewAdditionalServiceId()));
		
		this.rentalCarService.totalPriceCalculateAfterAddAdditionalService(response.getRentalCar().getRentalCarId());
		
		return new SuccessResult("OrderedAdditionalService.Updated");
	}

	@Override
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		
		checkIfNotExistAdditionalServiceInCarInRent(deleteOrderedAdditionalServiceRequest.getRentalCarId(),
				deleteOrderedAdditionalServiceRequest.getAdditionalServiceId());
		
		this.orderedAdditionalServiceDao.deleteById(this.orderedAdditionalServiceDao.getByRentalCar_rentalCarIdAndAdditionalService_additionalServiceId(deleteOrderedAdditionalServiceRequest.getRentalCarId(),
				deleteOrderedAdditionalServiceRequest.getAdditionalServiceId()).getOrderedAdditionalServiceId());
		
		return new SuccessResult("OrderedAdditionalService.Deleted");
	}

	@Override
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		
		var result = this.orderedAdditionalServiceDao.findAll();
		List<ListOrderedAdditionalServiceDto> response = result.stream().map(orderedAdditionalService ->this.modelMapperService.forDto()
				.map(orderedAdditionalService, ListOrderedAdditionalServiceDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListOrderedAdditionalServiceDto>>(response);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceByIdDto> getById(int orderedAdditionalServiceId) throws BusinessException {
		checkIfExist(orderedAdditionalServiceId);
		
		var result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedAdditionalServiceId);
		GetOrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(result, GetOrderedAdditionalServiceByIdDto.class);
		
		return new SuccessDataResult<GetOrderedAdditionalServiceByIdDto>(response);
	}

	@Override
	public DataResult<List<GetOrderedAdditionalServiceByRentalCarIdDto>> getAllByRentalCarId(int rentalCarId) throws BusinessException {
		this.rentalCarService.checkIfExistById(rentalCarId);
		
		var result = this.orderedAdditionalServiceDao.findOrderedAdditionalServicesByRentalCar_RentalCarId(rentalCarId);
		List<GetOrderedAdditionalServiceByRentalCarIdDto> response = result.stream().map(orderedAdditionalService ->this.modelMapperService.forDto()
				.map(orderedAdditionalService, GetOrderedAdditionalServiceByRentalCarIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetOrderedAdditionalServiceByRentalCarIdDto>>(response);
	}

	
	private boolean checkIfExistAdditionalServiceInCarInRent(int rentalCarId, int additionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarIdAndAdditionalService_additionalServiceId(rentalCarId, additionalServiceId);
		if(result != null) {
			throw new BusinessException("The additional service has been added to that car before.");
		}
		return true;
	}
	
	private boolean checkIfNotExistAdditionalServiceInCarInRent(int rentalCarId, int additionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByRentalCar_rentalCarIdAndAdditionalService_additionalServiceId(rentalCarId, additionalServiceId);
		if(result == null) {
			throw new BusinessException("The additional service can not find on that car.");
		}
		return true;
	}
	
	private boolean checkIfExist(int orderedAdditionalServiceId) throws BusinessException {
		var result = this.orderedAdditionalServiceDao.getByOrderedAdditionalServiceId(orderedAdditionalServiceId);
		if(result == null) {
			throw new BusinessException("Can not find ordered additional service id you wrote.");
		}
		return true;
	}
	
	public boolean checkIfRentalCarId(int rentalCarId) {
		var result = this.orderedAdditionalServiceDao.findOrderedAdditionalServicesByRentalCar_RentalCarId(rentalCarId);
		if(result.size() != 0) {
			return true;
		}
		return false;
	}
	
	private boolean checkIfRentalCarExistByRentalCarId(int rentalCarId) {
		this.rentalCarService.checkIfExistById(rentalCarId);
		return true;
	}
	
	private boolean checkIfAdditionalServiceExistByAdditionalServiceId(int additionalServiceId) {
		this.additionalServiceService.checkIfExistById(additionalServiceId);
		return true;
	}
	
}
