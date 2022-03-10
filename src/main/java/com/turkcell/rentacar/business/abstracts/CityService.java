package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CityByIdDto;
import com.turkcell.rentacar.business.dtos.CityListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CityService {
    DataResult<List<CityListDto>> getAll();

    Result add(CreateCityRequest createCityRequest) throws BusinessException;

    DataResult<CityByIdDto> getById(int id) throws BusinessException;

    Result update(UpdateCityRequest updateCityRequest) throws BusinessException;

    Result deleteById(DeleteCityRequest deleteCityRequest) throws BusinessException;
}
