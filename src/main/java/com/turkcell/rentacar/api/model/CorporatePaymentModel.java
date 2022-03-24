package com.turkcell.rentacar.api.model;

import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporatePaymentModel{
	CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer;
	CreateCreditCardRequest createCreditCardRequest;
	CreatePaymentRequest createPaymentRequest;
}
