package com.turkcellcamp.rentacar.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.GetOrderedAdditionalServiceByRentalCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.orderedAdditionalServiceDtos.ListOrderedAdditionalServiceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/orderedadditionalservices")
public class OrderedAdditionalServicesController {
	
	OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException {
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException {
		return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException {
		return this.orderedAdditionalServiceService.delete(deleteOrderedAdditionalServiceRequest);
	}
	
	@GetMapping("/getall")
	public DataResult<List<ListOrderedAdditionalServiceDto>> getAll() {
		return this.orderedAdditionalServiceService.getAll();
	}
	
	@GetMapping("/getbyid/{id}")
	public DataResult<GetOrderedAdditionalServiceByIdDto> getById(@RequestParam ("id") @Valid int id) throws BusinessException{
		return this.orderedAdditionalServiceService.getById(id);
	}
	
	@GetMapping("/getallbyrentalcarid/{rentalcarid}")
	public DataResult<List<GetOrderedAdditionalServiceByRentalCarIdDto>> getAllByRentalCarId(@RequestParam ("rentalCarId") int rentalCarId) throws BusinessException{
		return this.orderedAdditionalServiceService.getAllByRentalCarId(rentalCarId);
	}
}