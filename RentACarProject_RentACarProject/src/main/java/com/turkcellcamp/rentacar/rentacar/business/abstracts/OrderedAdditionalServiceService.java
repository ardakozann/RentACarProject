package com.turkcellcamp.rentacar.rentacar.business.abstracts;

import java.util.List;

import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByRentalCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

public interface OrderedAdditionalServiceService {
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException;
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException;
	public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException;
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll();
	public DataResult<GetOrderedAdditionalServiceByIdDto> getById(int rentalCarId) throws BusinessException;
	public DataResult <List<GetOrderedAdditionalServiceByRentalCarIdDto>> getAllByRentalCarId(int rentalCarId) throws BusinessException;
	public boolean checkIfRentalCarId(int rentalCarId);
}
