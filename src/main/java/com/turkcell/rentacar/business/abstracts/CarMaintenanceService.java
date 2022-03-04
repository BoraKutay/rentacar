package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CarMaintenanceByIdDto;
import com.turkcell.rentacar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentacar.business.requests.CreateCarMaintenanceRequest;
import com.turkcell.rentacar.business.requests.UpdateCarMaintenanceRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CarMaintenanceService {
    
	DataResult<List<CarMaintenanceListDto>> getAll();
    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);
    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
    DataResult<CarMaintenanceByIdDto> getById(int carMaintenanceId);
    Result deleteById(int carMaintenanceId);
	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);
}
