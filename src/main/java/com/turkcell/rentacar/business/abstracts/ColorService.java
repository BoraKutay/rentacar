package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<ColorByIdDto> getById(int id) throws BusinessException;

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result deleteById(DeleteColorRequest deleteColorRequest) throws BusinessException;
}
