package com.turkcell.rentacar.business.dtos.corporateCustomerDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerByIdDto {

	private String mail;
	
	private String companyName;
	
	private String taxNumber;
}
