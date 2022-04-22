package com.turkcell.rentacar.business.requests.createRequests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdditionalServiceRequest {
    
	@NotEmpty
	private String additionalServiceName;

	@PositiveOrZero
    private double dailyPrice;
}
