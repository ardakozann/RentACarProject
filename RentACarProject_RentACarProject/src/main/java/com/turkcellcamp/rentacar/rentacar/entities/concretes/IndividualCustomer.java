package com.turkcellcamp.rentacar.rentacar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "individual_customer_id", referencedColumnName = "customer_id")
@Table(name = "invidiual_customers")
public class IndividualCustomer extends Customer{
	
	 @Column(name = "first_name")
	private String firstName;
	
	 @Column(name = "last_name")
	private String lastName;
	
	 @Column(name = "identity_number")
	private String identityNumber;
		
}