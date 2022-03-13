package com.turkcell.rentacar.business.dtos.rentalDtos;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceByIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDtoById {

	private int rentalId;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String cityOfPickUpLocation_CityName;
	private String cityOfReturnLocation_CityName;
	
    private double additionalPrice;
	private int carCarId;
	
	private List<OrderedAdditionalServiceByIdDto> orderedAdditionalServices;
}
