package com.turkcellcamp.rentacar.rentacar.entities.concretes;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="rental_cars")
@Entity
public class RentalCar {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="rental_car_id")
	private int rentalCarId;
	
	@Column(name="rent_date")
	private LocalDate rentDate;
	
	@Column(name="return_date")
	private LocalDate returnDate;
	
	@ManyToOne
	@JoinColumn(name="car_id")
	private Car car;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private Customer customer;
	
	@Column(name="total_price")
	private double totalPrice;
	
	@OneToMany(mappedBy="rentalCar")
	private List<OrderedAdditionalService> orderedAdditionalServices;
	
	@ManyToOne
	@JoinColumn(name="rent_city_id")
	private City rentCity;
	
	@ManyToOne
	@JoinColumn(name="return_city_id")
	private City returnCity;
	
	@Column(name="rent_odometer")
	private long rentOdometer;
	
	@Column(name="return_odometer")
	private long returnOdometer;
	
	@OneToOne(mappedBy ="rentalCar")
	private Invoice invoice;
}

