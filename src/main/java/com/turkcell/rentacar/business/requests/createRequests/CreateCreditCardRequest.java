package com.turkcell.rentacar.business.requests.createRequests;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	@NotEmpty
	private String cardHolder;
	
	@CreditCardNumber
	private String cardNumber;
	
	@Size(min = 100,max = 999)
	private int cvv;
	
	@Min(1)
	@Max(12)
	private int month;
	
	@NotEmpty
	private int year;
	
	private double balance;
	
}
