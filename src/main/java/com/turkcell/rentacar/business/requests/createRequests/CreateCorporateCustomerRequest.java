package com.turkcell.rentacar.business.requests.createRequests;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.turkcell.rentacar.business.constants.messages.ValidationMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCorporateCustomerRequest {

	@Email(message = ValidationMessages.EMAIL_NOT_VALID)
	@NotNull
	private String mail;

	@NotEmpty
	private String password;
	
	@NotEmpty
	private String companyName;
	
	private String taxNumber;
}
