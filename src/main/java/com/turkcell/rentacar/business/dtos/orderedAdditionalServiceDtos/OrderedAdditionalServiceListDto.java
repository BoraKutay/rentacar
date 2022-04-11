package com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos;

import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceByIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceListDto {
	
	 private int orderedAdditionalServiceId;
	 private int additionalServiceId;
	 private AdditionalServiceByIdDto additionalService;
	 private int rentalRentalId;
}
