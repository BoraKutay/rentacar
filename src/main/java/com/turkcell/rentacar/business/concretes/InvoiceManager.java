package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.invoice.InvoiceNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rental;

@Service
public class InvoiceManager implements InvoiceService {
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private RentalService rentalService;
	
	@Autowired
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentalService rentalService) {
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.rentalService = rentalService;
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAll() {
		
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceListDto> response = result.stream().map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class) )
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response, BusinessMessages.INVOICES + BusinessMessages.LISTED);
	}
	
	@Override
	public DataResult<InvoiceByIdDto> getById(int id) throws BusinessException {
		
		checkIfInvoiceExists(id);
		
		Invoice result = this.invoiceDao.getById(id);
		InvoiceByIdDto response = this.modelMapperService.forDto().map(result, InvoiceByIdDto.class);
		
		return new SuccessDataResult<InvoiceByIdDto>(response, BusinessMessages.INVOICE + BusinessMessages.GET_BY_ID + id);
	}
	
	@Override
	@Transactional
	public Invoice add(int rentalId) throws BusinessException {
		
		Rental rental = this.rentalService.getRentalById(rentalId);
		Invoice invoice = new Invoice();
			
		setInvoiceFields(invoice,rental);
		
		return this.invoiceDao.save(invoice);
	}
	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceExists(deleteInvoiceRequest.getInvoiceNo());
		
		this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceNo());
		
		return new SuccessResult(BusinessMessages.INVOICE + BusinessMessages.DELETED);
	}

	@Override
	public Result update(int rentalId) throws BusinessException {
		
		Rental rental = this.rentalService.getRentalById(rentalId);
		Invoice invoice = this.modelMapperService.forRequest().map(rentalId, Invoice.class);
		
		setInvoiceFields(invoice,rental);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult(BusinessMessages.INVOICE + BusinessMessages.UPDATED);
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId) {
		
		List<Invoice> result = this.invoiceDao.getAllByCustomer_CustomerId(customerId);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,BusinessMessages.INVOICES + BusinessMessages.LISTED + BusinessMessages.BY_CUSTOMER);
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByBillingDateBetween(LocalDate startDate, LocalDate endDate) {
		
		List<Invoice> result = this.invoiceDao.getAllByBillingDateBetween(startDate, endDate);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,BusinessMessages.INVOICE + BusinessMessages.LISTED + BusinessMessages.BETWEEN + startDate + BusinessMessages.AND + endDate);
	}
	
    private boolean checkIfInvoiceExists(int id) throws BusinessException {
    	
    	if(invoiceDao.existsById(id) == false) {
    		
    		throw new InvoiceNotFoundException(BusinessMessages.INVOICE + BusinessMessages.DOES_NOT_EXISTS + id);
    		
    	}
		return true;
    }

	
	private void setInvoiceFields(Invoice invoice, Rental rental) throws BusinessException {
		
		invoice.setRentalDayNumber((int)ChronoUnit.DAYS.between(rental.getStartDate(), rental.getEndDate()));
		invoice.setCustomer(rental.getCustomer());
		invoice.setRental(rental);
		invoice.setBillingPrice(rental.getTotalPrice());
		invoice.setStartDateRental(rental.getStartDate());
		invoice.setEndDateRental(rental.getEndDate());
		invoice.setBillingDate(LocalDate.now());
		invoice.setInvoiceNo(invoice.getInvoiceNo());
		
	}




}
