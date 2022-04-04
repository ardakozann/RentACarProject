package com.turkcellcamp.rentacar.rentacar.business.requests.orderedAdditionalServiceRequests;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderedAdditionalServiceRequestForTransactional {
	
	private List<Integer> AdditionalServiceIds;
}
