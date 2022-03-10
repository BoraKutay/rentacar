package com.turkcell.rentacar.business.dtos;





import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceByIdDto {
	
	 private int additionalServiceId;
	
	 private int rentalRentalId;
	
}
