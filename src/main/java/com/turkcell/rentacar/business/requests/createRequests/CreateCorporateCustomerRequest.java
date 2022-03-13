package com.turkcell.rentacar.business.requests.createRequests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {

	private String mail;

	private String password;
	
	private String companyName;
	
	private String taxNumber;
}
