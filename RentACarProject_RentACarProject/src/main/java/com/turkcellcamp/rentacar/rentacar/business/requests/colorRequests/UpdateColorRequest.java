package com.turkcellcamp.rentacar.rentacar.business.requests.colorRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {
	private int colorId;
	private String colorName;
}
