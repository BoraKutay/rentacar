package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {
    
	DataResult<List<CarMaintenanceListDto>> getAll();
    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException;
    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
    DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId);
    Result deleteById(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest);
	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);
}
