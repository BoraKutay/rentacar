package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAdditionalServiceRequest {
	
	
	@NotNull
	@Positive
	private int additionalServiceId;

	@NotEmpty
    private String additionalServiceName;

	@PositiveOrZero
    private double dailyPrice;
}
