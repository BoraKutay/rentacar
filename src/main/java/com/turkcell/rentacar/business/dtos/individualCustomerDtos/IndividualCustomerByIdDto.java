package com.turkcell.rentacar.business.dtos.individualCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualCustomerByIdDto {
	
	private String mail;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;
}
