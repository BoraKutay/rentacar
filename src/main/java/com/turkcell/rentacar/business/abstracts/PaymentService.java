package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PaymentService {
	
    DataResult<List<PaymentListDto>> getAll();

    Result add(int rentalId) throws BusinessException;

    DataResult<PaymentByIdDto> getById(int id) throws BusinessException;

    Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException;

    Result deleteById(DeletePaymentRequest deletePaymentRequest) throws BusinessException;
}
