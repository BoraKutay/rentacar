package com.turkcell.rentacar.business.requests.createRequests;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderedAdditionalServiceRequest {
	
	private List<Integer> additionalServiceAdditionalServiceId;
	private int rentRentalId;
	
}
