package com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos;





import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceByIdDto {
	
	 private int additionalServiceId;
	 
	 private String additionalServiceName;

	 private double additionalServiceDailyPrice;
	
	 private int rentalRentalId;
	
}
