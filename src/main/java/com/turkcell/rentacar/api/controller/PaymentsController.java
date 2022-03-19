package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.api.model.CorporatePaymentModel;
import com.turkcell.rentacar.api.model.IndividualPaymentModel;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.external.abstracts.PosService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Rental;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

	private PaymentService paymentService;
	private PosService posService;
	private RentalService rentalService;
	private InvoiceService invoiceService;


	
	public PaymentsController(PaymentService paymentService, @Lazy PosService posService, RentalService rentalService,
			InvoiceService invoiceService) {
		
		this.paymentService = paymentService;
		this.posService = posService;
		this.rentalService = rentalService;
		this.invoiceService = invoiceService;
	}


	@GetMapping("/getAll")
    DataResult<List<PaymentListDto>> getAll() {
		return this.paymentService.getAll();
	}

	
	@Transactional
	@PostMapping("/add")
    Result addForIndividual(@RequestBody @Valid IndividualPaymentModel individualPaymentModel) throws BusinessException {
		
		this.posService.isCardValid(individualPaymentModel.getCreateCreditCardRequest());
		
		
		Rental rental = this.rentalService.addForIndividualCustomer(individualPaymentModel.getCreateRentalRequestForIndividualCustomer()).getData();
			
		this.posService.isPaymentSuccess(rental.getTotalPrice());
		
		this.invoiceService.add(rental.getRentalId());
		
		
		return this.paymentService.add(rental.getRentalId());
	}
	
	@Transactional
	@PostMapping("/addForCorporate")
    Result addForCorporate(@RequestBody @Valid CorporatePaymentModel corporatePaymentModel) throws BusinessException {
		
		
		return null;
	}
	
    @GetMapping("/getById")
    DataResult<PaymentByIdDto> getById(int id) throws BusinessException {
		return this.paymentService.getById(id);
	}


    @DeleteMapping("/delete")
    Result deleteById(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		return this.paymentService.deleteById(deletePaymentRequest);
	}
    

	
}
