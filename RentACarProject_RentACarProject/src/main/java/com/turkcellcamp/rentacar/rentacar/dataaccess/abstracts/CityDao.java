package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.City;
@Repository
public interface CityDao extends JpaRepository <City,Integer> {
	public City getByCityId(int cityId);
}