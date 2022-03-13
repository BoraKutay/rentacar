package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Invoice;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
	}
	@Override
	public DataResult<List<InvoiceListDto>> getAll() {
		
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceListDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class) )
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response, "Invoices are listed.");
	}
	
	@Override
	public DataResult<InvoiceByIdDto> getById(int id) throws BusinessException {
		
		checkIfInvoiceExists(id);
		
		Invoice result = this.invoiceDao.getById(id);
		InvoiceByIdDto response = this.modelMapperService.forDto().map(result, InvoiceByIdDto.class);
		
		return new SuccessDataResult<InvoiceByIdDto>(response, "Invoice is listed");
	}
	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) {
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice added.");
	}
	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExists(deleteInvoiceRequest.getInvoiceNo());
		
		this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceNo());
		
		return new SuccessResult("Invoice deleted.");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExists(updateInvoiceRequest.getInvoiceNo());
		
		Invoice invoice = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice updated");
	}
	
    private boolean checkIfInvoiceExists(int id) throws BusinessException {
    	if(invoiceDao.existsById(id) == false) {
    		throw new BusinessException("Invoice does not exists by id:" + id);
    	}
		return true;
    }

}
