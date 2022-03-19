package com.turkcell.rentacar.business.requests;


import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	private String cardHolder;
	
	@Size(min = 16,max = 16)
	private String cardNumber;
	
	private int cvv;
	
	private int month;
	
	private int year;
	
	private double balance;
	
}
