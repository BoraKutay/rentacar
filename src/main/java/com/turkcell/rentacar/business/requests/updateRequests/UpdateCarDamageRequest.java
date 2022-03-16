package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarDamageRequest {
	
	@NotNull
	@Positive
	private int carDamageId;
	
	@NotNull
	@Positive
	private int carId;
	
	@NotNull
	private String damageRecord;
}
