package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;

@Service
public class PaymentManager implements PaymentService{

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService) {
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
        List<Payment> result = this.paymentDao.findAll();

        List<PaymentListDto> response = result.stream()
                .map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, "Payments are listed successfully.");
	}

	@Override
	public Result add(int rentalId) throws BusinessException {

		Payment payment = new Payment();
		
		//DataResult<RentalDtoById> byId = this.rentalService.getById(rentalId).getData();
		payment.setCustomer(null);
		payment.setInvoice(null);
		payment.setOrderedAdditionalServices(null);
		payment.setRental(null);
		payment.setTotalAmount(150);
		this.paymentDao.save(payment);
		return new SuccessResult("saved");
	}

	@Override
	public DataResult<PaymentByIdDto> getById(int id) throws BusinessException {
		
		checkIfPaymentExists(id);
        
		Payment payment = this.paymentDao.getById(id);

        PaymentByIdDto response = this.modelMapperService.forDto().map(payment, PaymentByIdDto.class);

        return new SuccessDataResult<PaymentByIdDto>(response, "Payment is found by id.");
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result deleteById(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		checkIfPaymentExists(deletePaymentRequest.getPaymentId());
		
		this.paymentDao.deleteById(deletePaymentRequest.getPaymentId());
		
		return new SuccessResult("Payment deleted.");
	}
	
    private boolean checkIfPaymentExists(int id) throws BusinessException {
    	
    	if(paymentDao.existsById(id) == false) {
    		throw new BusinessException("Payment does not exists by id:" + id);
    	}
		return true;
    }
    

}
