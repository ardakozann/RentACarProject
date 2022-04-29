package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.CarMaintenance;
@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance, Integer> {
	List<CarMaintenance> getByCar_carId(int id);
	CarMaintenance getByCarMaintenanceId(int id);
	CarMaintenance getByReturnDateAndCar_carId(LocalDate localDate, int carId);
}