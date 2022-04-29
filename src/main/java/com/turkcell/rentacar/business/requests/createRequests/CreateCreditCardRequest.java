package com.turkcell.rentacar.business.requests.createRequests;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
	
	@NotEmpty
	private String cardHolder;
	
	@Size(min = 16,max = 16,message = "Must have 16 characters!")
	private String cardNumber;
	
	@Min(100)
	@Max(999)
	private int cvv;
	
	@Min(1)
	@Max(12)
	private int month;
	
	@NotNull
	private int year;
	
	
}
