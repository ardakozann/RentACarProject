package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenanceByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.GetCarMaintenancesInCarDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carMaintenanceDtos.ListCarMaintenanceDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.CreateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.DeleteCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carMaintenanceRequests.UpdateCarMaintenanceRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CarMaintenanceDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.CarMaintenance;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

	private CarMaintenanceDao carMaintenanceDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	private RentalCarService rentalCarService;
	
	
	@Autowired
	public CarMaintenanceManager(CarMaintenanceDao carMaintenanceDao, ModelMapperService modelMapperService, 
			@Lazy CarService carService, @Lazy RentalCarService rentalCarService) {
		this.carMaintenanceDao = carMaintenanceDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
		this.rentalCarService = rentalCarService;
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
		
		checkIfCarServiceExistByCarId(createCarMaintenanceRequest.getCarId());
		checkIfCarNotInRent(createCarMaintenanceRequest.getCarId());
		checkIfCarNotInMaintenance(createCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setCarMaintenanceId(0);
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Added");
	}
	
	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {		
		
		checkIfCarServiceExistByCarId(updateCarMaintenanceRequest.getCarId());
		checkIfCarInMaintenance(updateCarMaintenanceRequest.getCarId()); 
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest, CarMaintenance.class);
		carMaintenance.setCarMaintenanceId(this.carMaintenanceDao.getByReturnDateAndCar_carId(null,
				updateCarMaintenanceRequest.getCarId()).getCarMaintenanceId());
		
		this.carMaintenanceDao.save(carMaintenance);
		
		return new SuccessResult("CarMaintenance.Updated");
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {
		
		checkIfExistByCarMaintenanceId(deleteCarMaintenanceRequest.getCarMaintenanceId());
		
		this.carMaintenanceDao.deleteById(deleteCarMaintenanceRequest.getCarMaintenanceId());
		
		return new SuccessResult("CarMaintenance.Deleted");
	}

	@Override
	public DataResult<List<ListCarMaintenanceDto>> getAll() {
		
		var result = this.carMaintenanceDao.findAll();
		
		List<ListCarMaintenanceDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto()
				.map(carMaintenance, ListCarMaintenanceDto.class)).collect(Collectors.toList());
		setCarIds(result, response);
		
		return new SuccessDataResult <List<ListCarMaintenanceDto>>(response);
	}
	
	@Override
	public DataResult<GetCarMaintenanceByIdDto> getById(int carMaintenanceId) throws BusinessException{
		
		checkIfExistByCarMaintenanceId(carMaintenanceId);
		
		var result = this.carMaintenanceDao.getByCarMaintenanceId(carMaintenanceId);
		GetCarMaintenanceByIdDto response = this.modelMapperService.forDto().map(result, GetCarMaintenanceByIdDto.class);
		
		return new SuccessDataResult<GetCarMaintenanceByIdDto> (response);
	}
	
	@Override
	public DataResult<List<GetCarMaintenancesInCarDto>> getCarMaintenancesByCarId(int carId) {
		
		var result = this.carMaintenanceDao.getByCar_carId(carId);
		List<GetCarMaintenancesInCarDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto()
				.map(carMaintenance, GetCarMaintenancesInCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult <List<GetCarMaintenancesInCarDto>>(response);
	}
	
	
	private boolean checkIfExistByCarMaintenanceId(int id) throws BusinessException {
		if(this.carMaintenanceDao.getByCarMaintenanceId(id) != null) {
			return true;
		}
		throw new BusinessException("The car maintenance id you wrote is not exist.");
	}
	
	public boolean checkIfCarNotInMaintenance(int carId) throws BusinessException {	
		if(this.carMaintenanceDao.getByReturnDateAndCar_carId(null, carId) != null) {
			throw new BusinessException("The car in maintenance.");
		}
		return true;
	}
	
	private boolean checkIfCarInMaintenance(int carId)throws BusinessException {
		if(this.carMaintenanceDao.getByReturnDateAndCar_carId(null, carId) == null) {
			throw new BusinessException("The car is not in maintenance.");
		}
		return true;	
	}
	
	private void setCarIds(List<CarMaintenance> result, List<ListCarMaintenanceDto> response){
		for(int i = 0; i < result.size(); i++) {
			response.get(i).setCarId(result.get(i).getCar().getCarId());
		}
	}
	
	private void checkIfCarServiceExistByCarId(int carId) {
		this.carService.checkIfExistByCarId(carId);
	}
	
	private void checkIfCarNotInRent(int carId) {
		this.rentalCarService.checkIfCarNotInRent(carId);
	}
}
