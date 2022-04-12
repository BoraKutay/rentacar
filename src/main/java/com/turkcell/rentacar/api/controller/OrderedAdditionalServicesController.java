package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/ordered-additional-services")
public class OrderedAdditionalServicesController {
    
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	@Autowired
	public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	 @GetMapping("/getAll")
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll(){
    	return this.orderedAdditionalServiceService.getAll();
    }

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest) throws BusinessException{
		return this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
    	
    }

	 @GetMapping("/getById")
	public DataResult<OrderedAdditionalServiceByIdDto> getById(int id) throws BusinessException{
    	return this.orderedAdditionalServiceService.getById(id);
    }

	 @PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws BusinessException{
    	return this.orderedAdditionalServiceService.update(updateOrderedAdditionalServiceRequest);
    }

	 @DeleteMapping("/deleteById")
	public Result deleteById(@RequestBody @Valid DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws BusinessException{
    	return this.deleteById(deleteOrderedAdditionalServiceRequest);
    }
}
