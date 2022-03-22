package com.turkcell.rentacar.api.model;

import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporatePaymentModel{
	
	CreateCreditCardRequest createCreditCardRequest;
	CreatePaymentRequest createPaymentRequest;
}
