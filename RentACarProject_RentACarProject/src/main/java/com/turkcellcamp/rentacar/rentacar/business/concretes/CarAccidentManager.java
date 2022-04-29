package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarAccidentService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.constants.messages.BusinessMessage;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.GetCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.ListCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.CreateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.DeleteCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carAccidentRequests.UpdateCarAccidentRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CarAccidentDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.CarAccident;

@Service
public class CarAccidentManager implements CarAccidentService {

	private CarAccidentDao carAccidentDao;
	private ModelMapperService modelMapperService;
	private CarService carService;
	
	@Autowired
	public CarAccidentManager(CarAccidentDao carAccidentDao, ModelMapperService modelMapperService,
			CarService carService) {
		this.carAccidentDao = carAccidentDao;
		this.modelMapperService = modelMapperService;
		this.carService = carService;
	}

	@Override
	public Result add(CreateCarAccidentRequest createCarAccidentRequest) {
		
		checkIfCarExistByCarId(createCarAccidentRequest.getCarId());
		
		CarAccident carAccident = this.modelMapperService.forRequest().map(createCarAccidentRequest, CarAccident.class);
		
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult(BusinessMessage.CARACCIDENTSERVICE_ADD);
	}

	@Override
	public Result update(UpdateCarAccidentRequest updateCarAccidentRequest) {
		
		checkIfCarAccidentExistByCarAccidentId(updateCarAccidentRequest.getCarAccidentId());
		
		CarAccident carAccident = this.carAccidentDao.getByCarAccidentId(updateCarAccidentRequest.getCarAccidentId());
		carAccident.setCarAccidentDescription(updateCarAccidentRequest.getCarAccidentDescription());
		
		this.carAccidentDao.save(carAccident);
		
		return new SuccessResult(BusinessMessage.CARACCIDENTSERVICE_UPDATE);
	}

	@Override
	public Result delete(DeleteCarAccidentRequest deleteCarAccidentRequest) {

		checkIfCarAccidentExistByCarAccidentId(deleteCarAccidentRequest.getCarAccidentId());

		this.carAccidentDao.deleteById(deleteCarAccidentRequest.getCarAccidentId());
		
		return new SuccessResult(BusinessMessage.CARACCIDENTSERVICE_DELETE);
	}

	@Override
	public DataResult<List<ListCarAccidentDto>> getAll() {
		
		List<CarAccident> result = this.carAccidentDao.findAll();
		
		List<ListCarAccidentDto> response = result.stream().map(carAccident ->this.modelMapperService.forDto()
				.map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());
		response = toGetAllSetCarId(result, response);
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response);
	}

	@Override
	public DataResult<List<ListCarAccidentDto>> getCarAccidentsByCarId(int carId) {
		
		checkIfCarExistByCarId(carId);
		
		List<CarAccident> result = this.carAccidentDao.getByCar_CarId(carId);
		
		List<ListCarAccidentDto> response = result.stream().map(carAccident ->this.modelMapperService.forDto()
				.map(carAccident, ListCarAccidentDto.class)).collect(Collectors.toList());
		response = toGetAllSetCarId(result, response);
		
		return new SuccessDataResult<List<ListCarAccidentDto>>(response);
	}

	@Override
	public DataResult<GetCarAccidentDto> getCarAccidentsById(int carAccidentId) {

		CarAccident result = checkIfCarAccidentExistByCarAccidentId(carAccidentId);
		
		GetCarAccidentDto response = this.modelMapperService.forDto().map(result, GetCarAccidentDto.class);
		response.setCarId(result.getCar().getCarId());
		
		return new SuccessDataResult<GetCarAccidentDto>(response);
	}
	
	private List<ListCarAccidentDto> toGetAllSetCarId(List<CarAccident> result,List<ListCarAccidentDto> response) {
		for(int i = 0; i < result.size() ; i++) {
			response.get(i).setCarId(result.get(i).getCar().getCarId());		
		}
		return response;
	}
	
	private boolean checkIfCarExistByCarId(int carId) {
		if(this.carService.getById(carId).getData() == null) {
			throw new BusinessException(BusinessMessage.CARACCIDENTSERVICE_CHECKIFEXISTBYCARID_ERROR);
		}
		return true;
	}
	
	private CarAccident checkIfCarAccidentExistByCarAccidentId(int carAccidentId) {
		CarAccident carAccident = this.carAccidentDao.getByCarAccidentId(carAccidentId);
		if(carAccident == null) {
			throw new BusinessException(BusinessMessage.CARACCIDENTSERVICE_CHECKIFEXISTBYID_ERROR);
		}
		return carAccident;
	}
}