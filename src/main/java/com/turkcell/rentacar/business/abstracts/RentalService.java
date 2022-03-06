package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.RentalDtoById;
import com.turkcell.rentacar.business.dtos.RentalListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentalRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentalRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentalRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface RentalService {

	DataResult<List<RentalListDto>> getAll();
	
	DataResult<List<RentalListDto>> getAllByCarCarId(int id);
		
	Result add(CreateRentalRequest createRentalRequest) throws BusinessException;
	
	DataResult<RentalDtoById> getById(int id) throws BusinessException;
	
	Result update(UpdateRentalRequest updateRentalRequest) throws BusinessException;
	
	Result delete(DeleteRentalRequest deleteRentalRequest) throws BusinessException;
}
