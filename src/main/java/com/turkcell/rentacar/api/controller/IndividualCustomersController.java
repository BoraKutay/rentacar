package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individual-customers")
public class IndividualCustomersController {
	
	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}
	
	@GetMapping("/getAll")
	DataResult<List<IndividualCustomerListDto>> getAll(){
		
		return this.individualCustomerService.getAll();
		
	}
	
	@GetMapping("/getById/{id}")
	DataResult<IndividualCustomerByIdDto> getById(int id) throws BusinessException{
		return this.individualCustomerService.getById(id);
		
	}
	
	@PostMapping("/add")
	Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
		
	}
	
	@DeleteMapping("/delete")
	Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
		
	}
	
	@PutMapping("/update")
	Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
		
	}
	
}
