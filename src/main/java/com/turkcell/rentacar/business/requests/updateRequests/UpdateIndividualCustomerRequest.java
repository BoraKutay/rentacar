package com.turkcell.rentacar.business.requests.updateRequests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	
	private int individualCustomerId;
	
	private String mail;

	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;
}
