package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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

    private String additionalServiceName;

    private double dailyPrice;
}
