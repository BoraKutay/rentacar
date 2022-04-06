package com.turkcell.rentacar.api.model;

import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPaymentModel {
	CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer;
	CreateCreditCardRequest createCreditCardRequest;
	CreatePaymentRequest createPaymentRequest;
	
}
