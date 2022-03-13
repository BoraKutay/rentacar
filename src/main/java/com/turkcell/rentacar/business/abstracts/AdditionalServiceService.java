package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface AdditionalServiceService {
    DataResult<List<AdditionalServiceListDto>> getAll();

    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;

    DataResult<AdditionalServiceByIdDto> getById(int id) throws BusinessException;

    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;

    Result deleteById(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
}
