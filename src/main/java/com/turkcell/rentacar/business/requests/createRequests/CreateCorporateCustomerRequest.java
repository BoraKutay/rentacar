package com.turkcell.rentacar.business.requests.createRequests;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.turkcell.rentacar.business.constants.messages.ValidationMessages;
import com.turkcell.rentacar.business.constants.regularExpressions.RegularExpressions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {

	@Email(message = ValidationMessages.EMAIL_NOT_VALID,regexp = RegularExpressions.MAIL_REGEX)
	@NotNull
	private String mail;

	private String password;
	
	private String companyName;
	
	private String taxNumber;
}
