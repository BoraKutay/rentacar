package com.turkcell.rentacar.business.dtos.rentalDtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalListDto {
	
	private int rentalId;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String pickUpLocationCityName;
	private String returnLocationCityName;
	
	private double totalPrice;
	
	private int carCarId;
}
