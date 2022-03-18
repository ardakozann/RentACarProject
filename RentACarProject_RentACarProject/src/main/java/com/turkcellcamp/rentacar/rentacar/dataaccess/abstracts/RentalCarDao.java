package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.RentalCar;
@Repository
public interface RentalCarDao extends JpaRepository<RentalCar,Integer> {
	RentalCar getByRentalCarId(int id);
	RentalCar getByReturnDateAndCar_carId(LocalDate localDate, int carId);
	List<RentalCar> getByCar_carId(int carId);
}
