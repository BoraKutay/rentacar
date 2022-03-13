package com.turkcell.rentacar.business.requests.createRequests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
	private String mail;

	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;
}
