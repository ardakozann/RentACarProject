package com.turkcellcamp.rentacar.rentacar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "user_id")
@Table(name = "customers")
public class Customer extends User {
	
	@OneToMany(mappedBy = "customer")
	private List<RentalCar> rentalCars;
	
	@OneToMany(mappedBy = "customer")
	private List<Invoice> invoices;
	
	@OneToMany(mappedBy = "customer")
	private List<Card> cards;
}
