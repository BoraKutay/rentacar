package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.dtos.CarByIdDto;
import com.turkcell.rentacar.business.requests.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;


public interface CarService {
    DataResult<List<CarListDto>> getAll();

    Result add(CreateCarRequest createCarRequest);

    Result update(UpdateCarRequest createCarRequest);

    DataResult<CarByIdDto> getById(int carId);

    Result deleteById(int carId);

    DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

    DataResult<List<CarListDto>> getAllSorted(String ascOrDesc);

    DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice);
    
    DataResult<List<CarListDto>> getByModelYearIsLessThanEqual(int modelYear);
    
    
}
