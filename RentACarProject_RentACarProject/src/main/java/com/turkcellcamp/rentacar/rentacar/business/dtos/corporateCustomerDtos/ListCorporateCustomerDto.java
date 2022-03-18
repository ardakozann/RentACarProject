package com.turkcellcamp.rentacar.rentacar.business.dtos.corporateCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCorporateCustomerDto {
	private int id;
	private String email;
	private String password;
	private String companyName;
	private String taxNumber;
}
