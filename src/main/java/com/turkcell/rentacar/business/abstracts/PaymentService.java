package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.api.model.CorporatePaymentModel;
import com.turkcell.rentacar.api.model.IndividualPaymentModel;
import com.turkcell.rentacar.api.model.PaymentForLateFinishModel;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentByIdDto;
import com.turkcell.rentacar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface PaymentService {
	
    DataResult<List<PaymentListDto>> getAll();

    Result makePaymentForIndividualCustomer(IndividualPaymentModel individualPaymentModel) throws BusinessException;
    
    Result makePaymentForCorporateCustomer(CorporatePaymentModel corporatePaymentModel) throws BusinessException;

    DataResult<PaymentByIdDto> getById(int id) throws BusinessException;
    
    Result makePaymentForLateFinish(PaymentForLateFinishModel paymentForLateFinishModel) throws BusinessException;

}
