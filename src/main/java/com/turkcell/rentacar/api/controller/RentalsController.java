package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.RentalService;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.rentalDtos.RentalListDto;
import com.turkcell.rentacar.business.requests.FinishRentalRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForCorporateCustomer;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequestForIndividualCustomer;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentalRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentals")
public class RentalsController {

	private RentalService rentalService;
	
	
	public RentalsController(RentalService rentalService) {
		this.rentalService = rentalService;
	}

	@GetMapping("/getAll")
	DataResult<List<RentalListDto>> getAll(){
		return this.rentalService.getAll();
		
	}
	
	@GetMapping("/getAllByCarId")
	DataResult<List<RentalListDto>> getAllByCarCarId(int id) throws BusinessException{
		return this.rentalService.getAllByCarCarId(id);
		
	}
	
	
	@PostMapping("/addForIndividualCustomer")
	Result addForIndividualCustomer(CreateRentalRequestForIndividualCustomer createRentalRequestForIndividualCustomer) throws BusinessException {
		return this.rentalService.addForIndividualCustomer(createRentalRequestForIndividualCustomer);
	}
	
	@PostMapping("/addForCorporateCustomer")
	Result addForCorporateCustomer(CreateRentalRequestForCorporateCustomer createRentalRequestForCorporateCustomer) throws BusinessException {
		return this.rentalService.addForCorporateCustomer(createRentalRequestForCorporateCustomer);
	}
	
	
	@GetMapping("/getById")
	DataResult<RentalDtoById> getById(int id) throws BusinessException{
		return this.rentalService.getById(id);
		
	}
	
	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateRentalRequest updateRentalRequest) throws BusinessException{
		return this.rentalService.update(updateRentalRequest);
		
	}
	
	@DeleteMapping("/delete")
	Result delete(@RequestBody DeleteRentalRequest deleteRentalRequest) throws BusinessException {
		return this.rentalService.delete(deleteRentalRequest);
	}
	
	@PostMapping("/endRental")
	Result finishRental(FinishRentalRequest finishRentalRequest) throws BusinessException {
		return this.rentalService.finishRental(finishRentalRequest);
	}
}
