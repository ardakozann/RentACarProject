package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.Brand;

@Repository
public interface BrandDao extends JpaRepository<Brand, Integer> {
	Brand getByBrandName(String brandName);
	Brand getByBrandId(int brandId);
}