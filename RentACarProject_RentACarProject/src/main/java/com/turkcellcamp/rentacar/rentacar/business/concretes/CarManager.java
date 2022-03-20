package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.BrandService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.ColorService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByDailyPriceDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.GetCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.carDtos.ListCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.CreateCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.DeleteCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.carRequests.UpdateCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.CarDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService{

	private CarDao carDao;
	private ModelMapperService modelMapperService;
	private BrandService brandService;
	private ColorService colorService;
	
	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, BrandService brandService, ColorService colorService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.brandService = brandService;
		this.colorService = colorService;
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {		

		checkIfBrandIdAndColorIdExist(createCarRequest.getBrandId(), createCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);
		car.setOdometer(0);
		
		this.carDao.save(car);
		
		return new SuccessResult("Car.Added");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException {
		
		checkIfExistByCarId(updateCarRequest.getCarId());
		checkIfBrandIdAndColorIdExist(updateCarRequest.getBrandId(), updateCarRequest.getColorId());
		
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		this.carDao.save(car);
		
		return new SuccessResult("Car.Updated");	
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException {
		
		checkIfExistByCarId(deleteCarRequest.getCarId());
		
		this.carDao.deleteById(deleteCarRequest.getCarId());
		
		return new SuccessResult("Car.Deleted");
	}
	
	@Override
	public DataResult <List<ListCarDto>> getAll() {
		
		var result = this.carDao.findAll();
		List<ListCarDto> response = result.stream().map(car->this.modelMapperService.forDto()
				.map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListCarDto>>(response);
	}
	
	@Override
	public DataResult <GetCarByIdDto> getById(int carId) throws BusinessException {
		
		checkIfExistByCarId(carId);
		
		var result = this.carDao.getByCarId(carId);
		GetCarByIdDto response = this.modelMapperService.forDto().map(result, GetCarByIdDto.class);
		
		return new SuccessDataResult<GetCarByIdDto>(response);
	}

	@Override
	public DataResult<List<GetCarByDailyPriceDto>> getCarByDailyPrice(double dailyPrice) {
		
		var result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		
		if(result.size() != 0) {
			List<GetCarByDailyPriceDto> response = result.stream().map(car->this.modelMapperService.forDto()
				.map(car, GetCarByDailyPriceDto.class)).collect(Collectors.toList());
			return new SuccessDataResult<List<GetCarByDailyPriceDto>>(response);
		}
		return new ErrorDataResult<List<GetCarByDailyPriceDto>>("Can not find a car which is daily price you wrote is below");
	}

	@Override
	public DataResult<List<ListCarDto>> getAllPaged(int pageNumber, int pageSize) {
		
		Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
		
		List<Car> result = this.carDao.findAll(pageable).getContent();
		List <ListCarDto> response = result.stream().map(car -> 
		this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List <ListCarDto>>(response);
	}

	@Override
	public DataResult<List<ListCarDto>> getAllSorted() {
		
		Sort sort = Sort.by(Sort.Direction.DESC,"dailyPrice");
		//List<Car> result = this.carDao.findAll(Sort.by("dailyPrice").descending()); 
		
		List<Car> result = this.carDao.findAll(sort);
		List <ListCarDto> response = result.stream().map(car -> 
		this.modelMapperService.forDto().map(car, ListCarDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List <ListCarDto>>(response);
	}

	
	
	public boolean checkIfExistByCarId(int carId) throws BusinessException {
		if(this.carDao.getByCarId(carId) == null) {
			throw new BusinessException("The car you wrote id is not exist.");
		}
		else{
			return true;
		}
	}
	
	public void toSetCarOdometer(int carId, long odometer) {
		Car car = this.carDao.getByCarId(carId);
		car.setOdometer(odometer);
	}
	
	private void checkIfBrandIdAndColorIdExist(int brandId, int colorId) {
		this.brandService.checkIfExistByBrandId(brandId);
		this.colorService.checkIfExistByColorId(colorId);
	}
	
}
