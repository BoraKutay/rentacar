package com.turkcell.rentacar.business.requests;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishRentalRequest {
	
	@NotNull
	@Positive
	private int rentalId;
	
	@NotNull
	@Positive
	private int endKilometer;
}
