package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.dtos.ColorByIdDto;
import com.turkcell.rentacar.business.requests.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;

public interface ColorService {

    DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<ColorByIdDto> getById(int id);

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result deleteById(int colorId);
}
