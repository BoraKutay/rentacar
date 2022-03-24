package com.turkcell.rentacar.business.dtos.creditCardDtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardListDto {
	
	private int creditCarId;
	private String cardHolder;
	private String cardNumber;
	private int cvv;
	private int month;
	private int year;
	
}
