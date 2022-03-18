package com.turkcellcamp.rentacar.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcellcamp.rentacar.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CarService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CityService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.CustomerService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcellcamp.rentacar.rentacar.business.abstracts.RentalCarService;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByCarIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.GetRentalCarByIdDto;
import com.turkcellcamp.rentacar.rentacar.business.dtos.rentalCarDtos.ListRentalCarDto;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.CreateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.DeleteRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.business.requests.rentalCarRequests.UpdateRentalCarRequest;
import com.turkcellcamp.rentacar.rentacar.core.exceptions.BusinessException;
import com.turkcellcamp.rentacar.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.DataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.Result;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcellcamp.rentacar.rentacar.core.utilities.results.SuccessResult;
import com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts.RentalCarDao;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private ModelMapperService modelMapperService;	
	private CustomerService customerService;
	private CityService cityService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, @Lazy CarMaintenanceService carMaintenanceService,
			@Lazy CarService carService, ModelMapperService modelMapperService, AdditionalServiceService additionalServiceService,
			CustomerService customerService, CityService cityService, @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.rentalCarDao = rentalCarDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		this.cityService = cityService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public Result add(CreateRentalCarRequest createRentalCarRequest) throws BusinessException {
		
		checkIfCarExistByCarId(createRentalCarRequest.getCarId()); 
		checkIfCarMaintenanceNotExistByCarId(createRentalCarRequest.getCarId());
		checkIfCarNotInRent(createRentalCarRequest.getCarId());
		checkIfCityIdCorrect(createRentalCarRequest.getRentCityId());
		checkIfCityIdCorrect(createRentalCarRequest.getReturnCityId());
		checkIfReturnDateAfterRentDate(createRentalCarRequest.getRentDate(), createRentalCarRequest.getReturnDate());
		
		RentalCar rentalCar = this.modelMapperService.forRequest().map(createRentalCarRequest, RentalCar.class);
		rentalCar.setCustomer(this.customerService.getByUserId(createRentalCarRequest.getUserId()));
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		rentalCar.setRentOdometer(getCarOdometer(rentalCar.getCar().getCarId()));
		rentalCar.setReturnOdometer(0);
		
		
		this.rentalCarDao.save(rentalCar);
		return new SuccessResult("RentalCar.Added");
	}

	@Override
	public Result update(UpdateRentalCarRequest updateRentalCarRequest) throws BusinessException {
		
		checkIfExistById(updateRentalCarRequest.getRentalCarId());
		checkIfCityIdCorrect(updateRentalCarRequest.getReturnCityId());
		
		RentalCar rentalCar = toUpdate(updateRentalCarRequest);
		
		rentalCarDao.save(rentalCar);
		return new SuccessResult("RentalCar.Updated");
	}

	@Override
	public Result delete(DeleteRentalCarRequest deleteRentalCarRequest) throws BusinessException {
		
		checkIfExistById(deleteRentalCarRequest.getRentalCarId());
		
		this.rentalCarDao.deleteById(deleteRentalCarRequest.getRentalCarId());
		return new SuccessResult("RentalCar.Deleted");
	}

	@Override
	public DataResult<List<ListRentalCarDto>> getAll() {
		
		var result = this.rentalCarDao.findAll();
		List<ListRentalCarDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, ListRentalCarDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListRentalCarDto>>(response);
	}
	
	@Override
	public DataResult<List<GetRentalCarByCarIdDto>> getRentalCarByCarId(int carId) throws BusinessException {
		
		checkIfCarExistByCarId(carId);
		
		var result = this.rentalCarDao.getByCar_carId(carId);
		List<GetRentalCarByCarIdDto> response = result.stream().map(rentalCar ->this.modelMapperService.forDto()
				.map(rentalCar, GetRentalCarByCarIdDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetRentalCarByCarIdDto>>(response);
	}

	@Override
	public DataResult<GetRentalCarByIdDto> getRentalCarById(int rentalCarId) throws BusinessException {
		
		checkIfExistById(rentalCarId);
		
		var result = this.rentalCarDao.getByRentalCarId(rentalCarId);
		GetRentalCarByIdDto response = this.modelMapperService.forDto().map(result, GetRentalCarByIdDto.class);
		
		return new SuccessDataResult<GetRentalCarByIdDto>(response);
	}

	public boolean checkIfExistById(int rentalCarId) throws BusinessException{
		if(this.rentalCarDao.getByRentalCarId(rentalCarId) == null) {
			throw new BusinessException("Can not find rental car you wrote id");
		}
		return true;
	}
	
	public boolean checkIfCarNotInRent(int carId) throws BusinessException {
		if(this.rentalCarDao.getByReturnDateAndCar_carId(null, carId) != null) {
			throw new BusinessException("The car in rent.");
		}
		return true;
	}
	
	
	private double totalPriceCalculate(RentalCar rentalCar) {
		
		long daysBetween = ChronoUnit.DAYS.between(rentalCar.getRentDate(), rentalCar.getReturnDate());
		double totalPrice = daysBetween*this.carService.getById(rentalCar.getCar().getCarId()).getData().getDailyPrice();
		
		
		if(checkIfOrderedAdditionalService(rentalCar.getRentalCarId())) {
			
			for(OrderedAdditionalService orderedAdditionalService : rentalCar.getOrderedAdditionalServices()) {
				totalPrice += daysBetween*orderedAdditionalService.getAdditionalService().getAdditionalServiceDailyPrice();
				
			}
		}
		if(!rentalCar.getRentCity().equals(rentalCar.getReturnCity())) {
			totalPrice += 750;
			
		}
		
		return totalPrice;
	}

	public boolean checkIfReturnDateAfterRentDate(LocalDate rentDate, LocalDate returnDate) throws BusinessException {
		if(!rentDate.isBefore(returnDate)) {
			throw new BusinessException("Can not be return date before rent date.");
		}
		return true;
	}

	private RentalCar toUpdate (UpdateRentalCarRequest updateRentalCarRequest) {
		
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(updateRentalCarRequest.getRentalCarId());
		
		checkIfReturnDateAfterRentDate(rentalCar.getRentDate(), rentalCar.getReturnDate());
		
		rentalCar.setReturnCity(this.cityService.getByIdForOtherService(updateRentalCarRequest.getReturnCityId()));
		rentalCar.setReturnDate(updateRentalCarRequest.getReturnDate());
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		rentalCar.setReturnOdometer(updateRentalCarRequest.getReturnOdometer());
		setCarOdometer(rentalCar);
		
		return rentalCar;
	}
	
	private boolean checkIfCityIdCorrect(int cityId) {
		if(cityId > 0 && cityId < 82) {
			return true;
		}
		throw new BusinessException("The id you wrote is not city code.");
	}
	
	public void totalPriceCalculateAfterAddAdditionalService(int rentalCarId) {
		RentalCar rentalCar = this.rentalCarDao.getByRentalCarId(rentalCarId);
		rentalCar.setTotalPrice(totalPriceCalculate(rentalCar));
		this.rentalCarDao.save(rentalCar);
	}
	
	private boolean checkIfOrderedAdditionalService(int rentalCarId) {
		if(this.orderedAdditionalServiceService.checkIfRentalCarId(rentalCarId)) {
			return true;
		}
		return false;
	}
	
	private long getCarOdometer(int carId) {
		return this.carService.getById(carId).getData().getOdometer();
	}
	
	private void setCarOdometer(RentalCar rentalCar) {
		if(rentalCar.getRentOdometer() > rentalCar.getRentOdometer()) {
			throw new BusinessException("Rent odometer can not be under return odometer.");
		}
		this.carService.toSetCarOdometer(rentalCar.getCar().getCarId(), rentalCar.getReturnOdometer());
	}
	
	private boolean checkIfCarExistByCarId(int carId) {
		this.carService.checkIfExistByCarId(carId);
		return true;
	}
	
	private boolean checkIfCarMaintenanceNotExistByCarId(int carId) {
		this.carMaintenanceService.checkIfCarNotInMaintenance(carId);
		return true;
	}
}

	
