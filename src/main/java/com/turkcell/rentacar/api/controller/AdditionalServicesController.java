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

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.dtos.AdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additional-services")
public class AdditionalServicesController {
	private AdditionalServiceService additionalServiceService;

	@Autowired
	public AdditionalServicesController(AdditionalServiceService additionalServiceService) {
		this.additionalServiceService = additionalServiceService;
	}
	
	 @GetMapping("/getall")
    public DataResult<List<AdditionalServiceListDto>> getAll(){
		return this.additionalServiceService.getAll();
    	
    }

	 @PostMapping("/add")
    public Result add(@RequestBody @Valid CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException{
		return this.additionalServiceService.add(createAdditionalServiceRequest);
    	
    }
    
    @GetMapping("/getById")
    public DataResult<AdditionalServiceByIdDto> getById(int id) throws BusinessException{
		return this.additionalServiceService.getById(id);
    	
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException{
		return this.additionalServiceService.update(updateAdditionalServiceRequest);
    	
    }

    @DeleteMapping("/delete")
    public Result deleteById(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException{
		return this.additionalServiceService.deleteById(deleteAdditionalServiceRequest);
    	
    }
	
}
