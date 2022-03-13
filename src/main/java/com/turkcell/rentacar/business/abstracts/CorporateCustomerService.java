package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerByIdDto;
import com.turkcell.rentacar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface CorporateCustomerService {
	DataResult<List<CorporateCustomerListDto>> getAll();
	DataResult<CorporateCustomerByIdDto> getById(int id);
	Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest);
	Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest);
	Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest);
}
