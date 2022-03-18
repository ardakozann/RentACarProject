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
@PrimaryKeyJoinColumn(name = "corporate_customer_id", referencedColumnName = "customer_id")
@Table(name = "corporate_customers")
public class CorporateCustomer extends Customer{
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "tax_number")
	private String taxNumber;

}