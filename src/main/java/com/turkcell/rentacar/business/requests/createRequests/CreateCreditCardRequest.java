package com.turkcell.rentacar.business.requests.createRequests;


import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	private String cardHolder;
	
	@CreditCardNumber
	private String cardNumber;
	
	private int cvv;
	
	private int month;
	
	private int year;
	
	private double balance;
	
}
