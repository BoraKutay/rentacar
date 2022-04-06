package com.turkcell.rentacar.business.requests.updateRequests;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	@NotNull
	private int individualCustomerId;
	
	@Email(message = "Email is not valid!",regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&’*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$")
	@NotNull
	private String mail;

	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;
}
