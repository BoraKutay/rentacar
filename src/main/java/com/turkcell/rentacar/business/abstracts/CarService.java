package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.CarByIdDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCarRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;


public interface CarService {
    DataResult<List<CarListDto>> getAll();

    Result add(CreateCarRequest createCarRequest);

    Result update(UpdateCarRequest createCarRequest);

    DataResult<CarByIdDto> getById(int carId);

    Result deleteById(DeleteCarRequest deleteCarRequest);

    DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSorted(String ascOrDesc);

    DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice);
    
    DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear);
    
    
}
