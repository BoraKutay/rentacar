package com.turkcell.rentacar.business.requests.updateRequests;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	@NotNull
	@Positive
	private int additionalServiceId;
	
	@NotNull
	@Positive
	private int rentalRentalId;
}
