package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;


public interface InvoiceService {
	DataResult<List<InvoiceListDto>> getAll();
	DataResult<InvoiceByIdDto> getById(int id) throws BusinessException;
	Result add(int id) throws BusinessException;
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;
	DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId);
	DataResult<List<InvoiceListDto>> getAllByBillingDateBetween(LocalDate startDate, LocalDate endDate);
	Invoice getInvoiceById(int id) throws BusinessException;
}
