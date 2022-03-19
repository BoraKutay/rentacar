package com.turkcell.rentacar.api.model;

import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;

public class CorporatePaymentModel {
	CreateCreditCardRequest createCreditCardRequest;
	CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer;
}
