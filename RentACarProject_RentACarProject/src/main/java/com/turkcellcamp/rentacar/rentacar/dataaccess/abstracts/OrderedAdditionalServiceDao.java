package com.turkcellcamp.rentacar.rentacar.dataaccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcellcamp.rentacar.rentacar.entities.concretes.OrderedAdditionalService;
@Repository
public interface OrderedAdditionalServiceDao extends JpaRepository <OrderedAdditionalService,Integer> {
	public OrderedAdditionalService getByOrderedAdditionalServiceId(int orderedAdditionalServiceId);
	public OrderedAdditionalService getByRentalCar_rentalCarIdAndAdditionalService_additionalServiceId(int rentalCarId, int additionalServiceId);
	public List<OrderedAdditionalService> findOrderedAdditionalServicesByRentalCar_RentalCarId(int rentalCarId);
}
