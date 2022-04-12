package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporate-customers")

public class CorporateCustomersController {
	
	private CorporateCustomerService corporateCustomerService;
	
	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}
	
	@GetMapping("/getAll")
	DataResult<List<CorporateCustomerListDto>> getAll(){
		
		return this.corporateCustomerService.getAll();
		
	}
	
	@GetMapping("/getById/{id}")
	DataResult<CorporateCustomerByIdDto> getById(int id) throws BusinessException{
		return this.corporateCustomerService.getById(id);
		
	}
	
	@PostMapping("/add")
	Result add(@RequestBody @Valid @Email CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
		
	}
	
	@DeleteMapping("/deleteById")
	Result delete(@RequestBody @Valid DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException{
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
		
	}
	
	@PutMapping("/update")
	Result update(@RequestBody @Valid @Email UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException{
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
		
	}
	
	

}
