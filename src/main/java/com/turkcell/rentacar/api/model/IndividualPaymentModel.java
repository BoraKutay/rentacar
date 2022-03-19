package com.turkcell.rentacar.api.model;

import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualPaymentModel {
	
	CreateCreditCardRequest createCreditCardRequest;
	CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer;
	
}
