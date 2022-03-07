package com.turkcell.rentacar.business.dtos;

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
	
	private int carCarId;
}