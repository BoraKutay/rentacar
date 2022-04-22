package com.turkcell.rentacar.api.model;

import javax.validation.Valid;

import com.turkcell.rentacar.business.requests.createRequests.CreateCreditCardRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentForLateFinishModel {
	@Valid
	CreateCreditCardRequest createCreditCardRequest;
	CreatePaymentRequest createPaymentRequest;
}
