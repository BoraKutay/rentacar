package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.api.model.CorporatePaymentModel;
import com.turkcell.rentacar.api.model.IndividualPaymentModel;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.CreateCreditCardRequest;
import com.turkcell.rentacar.core.adapters.abstracts.PosAdapterService;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService{

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private PosAdapterService posAdapterService;
	private InvoiceService invoiceService;
	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,PosAdapterService posAdapterService,RentalService rentalService,InvoiceService invoiceService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.posAdapterService = posAdapterService;
		this.invoiceService = invoiceService;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
        List<Payment> result = this.paymentDao.findAll();

        List<PaymentListDto> response = result.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.PAYMENTS + BusinessMessages.LIST);
	}

	@Override
	@Transactional
	public Result makePaymentForIndividualCustomer(IndividualPaymentModel individualPaymentModel) throws BusinessException {

		checkIfCreditCardIsValid(individualPaymentModel.getCreateCreditCardRequest());
		
		Payment payment = this.modelMapperService.forRequest().map(individualPaymentModel.getCreatePaymentRequest(), Payment.class);
		Rental rental = this.rentalService.getRentalById(individualPaymentModel.getCreatePaymentRequest().getRentalId());
		
		this.posAdapterService.isPaymentSuccess(rental.getTotalPrice());
		this.invoiceService.add(rental.getRentalId());
		
		setPaymentFields(payment,rental);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(BusinessMessages.PAYMENT + BusinessMessages.ADD);
	}
	
	

	@Override
	@Transactional
	public Result makePaymentForCorporateCustomer(CorporatePaymentModel corporatePaymentModel) throws BusinessException {
		
		
		checkIfCreditCardIsValid(corporatePaymentModel.getCreateCreditCardRequest());
		
		Payment payment = this.modelMapperService.forRequest().map(corporatePaymentModel.getCreatePaymentRequest(), Payment.class);
		Rental rental = this.rentalService.getRentalById(corporatePaymentModel.getCreatePaymentRequest().getRentalId());
		
		this.posAdapterService.isPaymentSuccess(rental.getTotalPrice());
		this.invoiceService.add(rental.getRentalId());
		
		setPaymentFields(payment,rental);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(BusinessMessages.PAYMENT + BusinessMessages.ADD);
		
	}

	@Override
	public DataResult<PaymentByIdDto> getById(int id) throws BusinessException {
		
		checkIfPaymentExists(id);
        
		Payment payment = this.paymentDao.getById(id);

        PaymentByIdDto response = this.modelMapperService.forDto().map(payment, PaymentByIdDto.class);

        return new SuccessDataResult<PaymentByIdDto>(response, BusinessMessages.PAYMENT + BusinessMessages.GET_BY_ID + id);
	}
	
    private boolean checkIfPaymentExists(int id) throws BusinessException {
    	
    	if(paymentDao.existsById(id) == false) {
    		throw new BusinessException(BusinessMessages.PAYMENT + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
    }
    
    private void setPaymentFields(Payment payment, Rental rental) {
    	
    	payment.setCustomer(rental.getCustomer());
		payment.setOrderedAdditionalServices(rental.getOrderedAdditionalServices());
		payment.setRental(rental);
		payment.setTotalAmount(rental.getTotalPrice());
		
    }
    
    private void checkIfCreditCardIsValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {	
    	
    	this.posAdapterService.isCardValid(createCreditCardRequest.getCardHolder(), 
    			createCreditCardRequest.getCardNumber(),
    			createCreditCardRequest.getCvv(),
    			createCreditCardRequest.getMonth(),
    			createCreditCardRequest.getYear());
		
    }
    
    

    

    

}
