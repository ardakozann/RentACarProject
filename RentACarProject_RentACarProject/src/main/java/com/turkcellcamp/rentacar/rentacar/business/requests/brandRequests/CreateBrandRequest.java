package com.turkcellcamp.rentacar.rentacar.business.requests.brandRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateBrandRequest {
	private String brandName;

}