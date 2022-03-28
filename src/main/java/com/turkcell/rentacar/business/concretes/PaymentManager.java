package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.api.model.CorporatePaymentModel;
import com.turkcell.rentacar.api.model.IndividualPaymentModel;
import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.core.adapters.abstracts.PosAdapterService;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class PaymentManager implements PaymentService{

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	private PosAdapterService posAdapterService;
	private InvoiceService invoiceService;
	private CreditCardService creditCardService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,PosAdapterService posAdapterService,
			RentalService rentalService,InvoiceService invoiceService,CreditCardService creditCardService, OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
		this.posAdapterService = posAdapterService;
		this.invoiceService = invoiceService;
		this.creditCardService = creditCardService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
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
	@Transactional(rollbackFor = BusinessException.class)
	public Result makePaymentForIndividualCustomer(IndividualPaymentModel individualPaymentModel) throws BusinessException {

		posAdapterService.isCardValid(individualPaymentModel.getCreateCreditCardRequest());
		
		runPaymentSuccessorForIndividual(individualPaymentModel);
		
		
		return new SuccessResult(BusinessMessages.PAYMENT + BusinessMessages.ADD);
	}
	
	

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public Result makePaymentForCorporateCustomer(CorporatePaymentModel corporatePaymentModel) throws BusinessException {
			
		posAdapterService.isCardValid(corporatePaymentModel.getCreateCreditCardRequest());
			
		runPaymentSuccessorForCorporate(corporatePaymentModel);
		
		return new SuccessResult(BusinessMessages.PAYMENT + BusinessMessages.ADD);
		
	}
	
	@Transactional(rollbackFor = BusinessException.class)
	public void runPaymentSuccessorForIndividual(IndividualPaymentModel individualPaymentModel) throws BusinessException {
		
		Payment payment = this.modelMapperService.forRequest().map(individualPaymentModel.getCreatePaymentRequest(), Payment.class);
		Rental rental = this.rentalService.addForIndividualCustomer(individualPaymentModel.getCreateRentalRequestForIndividualCustomer()).getData();
		Invoice invoice = this.invoiceService.add(rental.getRentalId());
		
		payment.setInvoice(invoice);
		payment.setRental(rental);
		setPaymentFields(payment, rental);
		
		this.posAdapterService.makePayment(individualPaymentModel.getCreateCreditCardRequest(), rental.getTotalPrice());
		
		this.paymentDao.save(payment);
		
	}
	
	@Transactional(rollbackFor = BusinessException.class)
	public void runPaymentSuccessorForCorporate(CorporatePaymentModel corporatePaymentModel) throws BusinessException {
			
		Payment payment = this.modelMapperService.forRequest().map(corporatePaymentModel.getCreatePaymentRequest(), Payment.class);
		Rental rental = this.rentalService.addForCorporateCustomer(corporatePaymentModel.getCreateRentalRequestForCorporateCustomer()).getData();
		Invoice invoice = this.invoiceService.add(rental.getRentalId());
		
		payment.setInvoice(invoice);
		payment.setRental(rental);
		setPaymentFields(payment, rental);
		
		this.posAdapterService.makePayment(corporatePaymentModel.getCreateCreditCardRequest(), rental.getTotalPrice());
		
		this.paymentDao.save(payment);
		
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
		payment.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getAllByRentalId(rental.getRentalId()));
		payment.setRental(rental);
		payment.setTotalAmount(rental.getTotalPrice());
		
    }
    
      

    

}
