package com.turkcell.rentacar.business.requests.updateRequests;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentacar.business.constants.messages.ValidationMessages;
import com.turkcell.rentacar.business.constants.regularExpressions.RegularExpressions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
	
	@NotNull
	@Positive
	private int individualCustomerId;
	
	@Email(message = ValidationMessages.EMAIL_NOT_VALID,regexp = RegularExpressions.MAIL_REGEX)
	@NotNull
	private String mail;

	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private String nationalIdentity;
}
