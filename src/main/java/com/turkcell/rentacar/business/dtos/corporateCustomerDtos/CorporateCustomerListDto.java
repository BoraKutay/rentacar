package com.turkcell.rentacar.business.dtos.corporateCustomerDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerListDto {
	
	private String companyName;
	
	private String taxNumber;
}
