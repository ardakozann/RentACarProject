package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.AdditionalService;
@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer>{
	public AdditionalService getByAdditionalServiceId(int id);
	public AdditionalService getByAdditionalServiceName(String name);
}