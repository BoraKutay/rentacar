package com.turkcell.rentacar.business.requests.updateRequests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	private int corporateCustomerId;
	
	private String mail;

	private String password;
	
	private String companyName;
	
	private String taxNumber;
}
