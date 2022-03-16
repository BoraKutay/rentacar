package com.turkcell.rentacar.api.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceByIdDto;
import com.turkcell.rentacar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {
	
	private InvoiceService invoiceService;

	@Autowired
	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}
	
	@GetMapping("/getAll")
	DataResult<List<InvoiceListDto>> getAll(){
		
		return this.invoiceService.getAll();
		
	}
	
	@GetMapping("/getById/{id}")
	DataResult<InvoiceByIdDto> getById(int id) throws BusinessException{
		return this.invoiceService.getById(id);
		
	}
	
	@PostMapping("/add")
	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		return this.invoiceService.add(createInvoiceRequest);
		
	}
	
	@DeleteMapping("/delete")
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException{
		return this.invoiceService.delete(deleteInvoiceRequest);
		
	}
	
	@PutMapping("/update")
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException{
		return this.invoiceService.update(updateInvoiceRequest);
		
	}
	@GetMapping("/getAllByCustomerId")
	DataResult<List<InvoiceListDto>> getAllByCustomer_CustomerId(int customerId){
		return this.invoiceService.getAllByCustomer_CustomerId(customerId);
		
	}
	
	@GetMapping("/getBillingsDateBetween")
	DataResult<List<InvoiceListDto>> getAllByBillingDateBetween(LocalDate startDate, LocalDate endDate){
		return this.invoiceService.getAllByBillingDateBetween(startDate, endDate);
		
	}
}
