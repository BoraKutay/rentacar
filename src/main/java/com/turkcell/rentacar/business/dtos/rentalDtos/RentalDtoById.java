package com.turkcell.rentacar.business.dtos.rentalDtos;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.entities.concretes.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDtoById {
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String pickUpLocationCityName;
	private String returnLocationCityName;
	
    private double totalPrice;
	private int carCarId;
	
	private Customer customer;
	
	private List<OrderedAdditionalServiceByIdDto> orderedAdditionalServices;
}