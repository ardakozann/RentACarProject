package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.business.dtos.carAccidentDtos.GetCarAccidentDto;
import com.turkcellcamp.rentacar.rentacar.entities.concretes.CarAccident;
@Repository
public interface CarAccidentDao extends JpaRepository <CarAccident, Integer> {
	
	public CarAccident getByCarAccidentId (int carAccidentId);
	public List<CarAccident> getByCar_CarId (int carId);
}
