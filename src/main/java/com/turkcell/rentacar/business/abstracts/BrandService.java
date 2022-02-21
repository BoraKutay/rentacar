package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.requests.dtos.BrandByIdDto;
import com.turkcell.rentacar.business.requests.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

import java.util.List;


public interface BrandService {
    DataResult<List<BrandListDto>> getAll();

    Result add(CreateBrandRequest createBrandRequest) throws BusinessException;

    DataResult<BrandByIdDto> getById(int id);

    Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException;

    Result deleteById(int brandId);
}
