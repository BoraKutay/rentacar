package com.turkcell.rentacar.business.requests.updateRequests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
	
	
	@NotNull
	@Positive
	private int creditCardId;
	
	@NotEmpty
	private String cardHolder;
	
	@Size(min = 16,max = 16)
	private String cardNumber;
	
	@Size(min = 100,max = 999)
	private int cvv;
	
	@Min(1)
	@Max(12)
	private int month;
	
	@NotEmpty
	private int year;
	

}
