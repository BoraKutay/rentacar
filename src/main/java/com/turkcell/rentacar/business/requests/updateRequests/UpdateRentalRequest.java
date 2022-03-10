package com.turkcell.rentacar.business.requests.updateRequests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRentalRequest {

	@NotNull
	@Positive
	private int rentalId;
	
	@NotNull
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@NotNull
	@Positive
	private int carCarId;
	
	@NotNull
	@Positive
	private int orderedAdditionalServiceId;
	
	@NotNull
	@Positive
	private int cityOfPickUpLocationId;
	
	@NotNull
	@Positive
	private int cityOfReturnLocationId;
}
