package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.turkcell.rentacar.business.constants.messages.ValidationMessages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCorporateCustomerRequest {
	
	
	@NotNull
	@Positive
	private int corporateCustomerId;
	
	@Email(message = ValidationMessages.EMAIL_NOT_VALID)
	@NotNull
	private String mail;

	@NotEmpty
	private String password;
	
	@NotEmpty
	private String companyName;
	
	private String taxNumber;
}
