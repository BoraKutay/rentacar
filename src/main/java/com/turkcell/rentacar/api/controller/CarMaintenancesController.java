package com.turkcell.rentacar.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentacar.business.dtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
	
	private CarMaintenanceService carMaintenanceService;
	
	public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
		this.carMaintenanceService =carMaintenanceService;
		
	}
	
	
	 @GetMapping("/getall")
	    public DataResult<List<CarMaintenanceListDto>> getAll() {
	        return this.carMaintenanceService.getAll();
	    }

	    @PostMapping("/add")
	    public Result add(@RequestBody @Valid CreateCarMaintenanceRequest createcarMaintenanceRequest) throws BusinessException {

	        return this.carMaintenanceService.add(createcarMaintenanceRequest);
	    }

	    @GetMapping("/getbyid")
	    public DataResult<CarMaintenanceByIdDto> getById(@RequestParam(required = true) int carMaintenanceId) throws BusinessException {
	        return this.carMaintenanceService.getById(carMaintenanceId);
	    }

	    @PutMapping("/update")
	    public Result update(@RequestBody @Valid UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException {
	        return this.carMaintenanceService.update(updateCarMaintenanceRequest);
	    }

	    @DeleteMapping("/deletebyid")
	    public Result deleteById(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException {

	        return this.carMaintenanceService.deleteById(deleteCarMaintenanceRequest);
	    }
	    
	    @GetMapping("/getbycarid")
	    public DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam(required = true) int carId) throws BusinessException {
	        return this.carMaintenanceService.getByCarId(carId);
	    }


}
