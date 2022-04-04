package com.turkcell.rentacar.business.requests.createRequests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalRequestForIndividualCustomer {
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate startDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate endDate;
	
	@NotNull
	@PositiveOrZero
	private int car_CarId;
	
	@NotNull
	@Positive
	private int individualCustomerId;
	
	@NotNull
	@PositiveOrZero
	private int pickUpLocationIdCityId;
	
	@NotNull
	@PositiveOrZero
	private int returnLocationIdCityId;
	
	@Nullable
	private List<Integer> additionalServicesId;
}
