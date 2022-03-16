package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
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
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		RentalDtoById rentalDtoById = this.rentalService.getById(createInvoiceRequest.getRentalId()).getData();
	
		setInvoiceFields(invoice,rentalDtoById);
		
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
		RentalDtoById rentalDtoById = this.rentalService.getById(updateInvoiceRequest.getRentalId()).getData();
		
		setInvoiceFields(invoice,rentalDtoById);
		
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice updated");
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId) {
		
		List<Invoice> result = this.invoiceDao.getAllByCustomer_CustomerId(customerId);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices by customer are listed.");
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByBillingDateBetween(LocalDate startDate, LocalDate endDate) {
		
		List<Invoice> result = this.invoiceDao.getAllByBillingDateBetween(startDate, endDate);
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto().map(invoice, InvoiceListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices by customer are listed.");
	}
	
    private boolean checkIfInvoiceExists(int id) throws BusinessException {
    	if(invoiceDao.existsById(id) == false) {
    		throw new BusinessException("Invoice does not exists by id:" + id);
    	}
		return true;
    }

	
	private void setInvoiceFields(Invoice invoice, RentalDtoById rentalDtoById) {
		
		invoice.setRentalDayNumber((int)ChronoUnit.DAYS.between(rentalDtoById.getStartDate(), rentalDtoById.getEndDate()));
		invoice.setCustomer(rentalDtoById.getCustomer());
		invoice.setBillingPrice(rentalDtoById.getTotalPrice());
		invoice.setStartDateRental(rentalDtoById.getStartDate());
		invoice.setEndDateRental(rentalDtoById.getEndDate());
		invoice.setBillingDate(LocalDate.now());
		invoice.setInvoiceNo(0);
	}


}
