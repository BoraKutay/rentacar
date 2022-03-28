package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.api.model.CorporatePaymentModel;
import com.turkcell.rentacar.api.model.IndividualPaymentModel;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;

	
	public PaymentsController(PaymentService paymentService) {
		
		this.paymentService = paymentService;

	}


	@GetMapping("/getAll")
    DataResult<List<PaymentListDto>> getAll() {
		return this.paymentService.getAll();
	}

	
	@Transactional
	@PostMapping("/makePaymentForIndividualCustomer")
    Result makePaymentForIndividualCustomer(@RequestBody @Valid IndividualPaymentModel individualPaymentModel) throws BusinessException {
		return this.paymentService.makePaymentForIndividualCustomer(individualPaymentModel);
	}
	
	@Transactional
	@PostMapping("/makePaymentForCorporateCustomer")
    Result makePaymentForCorporateCustomer(@RequestBody @Valid CorporatePaymentModel corporatePaymentModel) throws BusinessException {	
		return this.paymentService.makePaymentForCorporateCustomer(corporatePaymentModel);
	}
	
    @GetMapping("/getById")
    DataResult<PaymentByIdDto> getById(int id) throws BusinessException {
		return this.paymentService.getById(id);
	}


    

	
}
