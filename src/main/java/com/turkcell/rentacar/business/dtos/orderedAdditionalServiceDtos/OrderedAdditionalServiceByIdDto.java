package com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos;





import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceByIdDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceByIdDto {
	
	 private AdditionalServiceByIdDto additionalService;
	 private int additionalServiceId;
	 private int rentalRentalId;
	
}
