package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequest {

	@NotNull
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	@NotNull
	@PositiveOrZero
	private int car_CarId;
	
	
	@NotNull
	@PositiveOrZero
	private int pickUpLocationIdCityId;
	
	@NotNull
	@PositiveOrZero
	private int returnLocationIdCityId;
	

	private List<Integer> additionalServicesId;

}



