package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.additionalService.AdditionalServiceNotFoundException;
import com.turkcell.rentacar.core.exceptions.additionalService.AdditionalServiceNotUniqueException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.AdditionalService;


@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
		this.additionalServiceDao = additionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		
        List<AdditionalService> result = this.additionalServiceDao.findAll();
        
        List<AdditionalServiceListDto> response = result.stream()
                .map(additionalService -> this.modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<AdditionalServiceListDto>>(response, BusinessMessages.ADDITIONAL_SERVICES + BusinessMessages.LISTED);
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
		
		checkIfAdditionalServiceNameIsExists(createAdditionalServiceRequest.getAdditionalServiceName());
		
		this.additionalServiceDao.save(additionalService);
		
		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE + BusinessMessages.ADDED);

	}

	@Override
	public DataResult<AdditionalServiceByIdDto> getById(int id) throws BusinessException {
        
		checkIfAdditionalServiceExists(id);
		
		AdditionalService additionalService = this.additionalServiceDao.getById(id);

        AdditionalServiceByIdDto response = this.modelMapperService.forDto().map(additionalService, AdditionalServiceByIdDto.class);
        
		return new SuccessDataResult<AdditionalServiceByIdDto>(response,BusinessMessages.ADDITIONAL_SERVICE + BusinessMessages.GET_BY_ID + id);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getAdditionalServiceId());
		
		AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
		
		checkIfAdditionalServiceNameIsExists(updateAdditionalServiceRequest.getAdditionalServiceName());
		
        this.additionalServiceDao.save(additionalService);
        
        return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE + BusinessMessages.UPDATED);
	}

	@Override
	public Result deleteById(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
		
		checkIfAdditionalServiceExists(deleteAdditionalServiceRequest.getAdditionalServiceId());
		
        this.additionalServiceDao.deleteById(deleteAdditionalServiceRequest.getAdditionalServiceId());
        
        return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE + BusinessMessages.DELETED);
	}
	
	
    private void checkIfAdditionalServiceNameIsExists(String additionalServiceName)throws BusinessException {
    	
        for (AdditionalServiceListDto additionalServiceElement : this.getAll().getData()) {
            if (additionalServiceElement.getAdditionalServiceName().equalsIgnoreCase(additionalServiceName)) {
                throw new AdditionalServiceNotUniqueException(BusinessMessages.NOT_UNIQUE + BusinessMessages.ADDITIONAL_SERVICE);
            }
        }
    }
	
    public boolean checkIfAdditionalServiceExists(int id) throws BusinessException {
    	
    	if(additionalServiceDao.existsById(id) == false) {
    		throw new AdditionalServiceNotFoundException(BusinessMessages.ADDITIONAL_SERVICE + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
    }
    
	public void checkIfAdditionalServicesExists(List<Integer> additionalServicesId) throws BusinessException {
		for(int id = 0; id < additionalServicesId.size();id++) {
			checkIfAdditionalServiceExists(id);
		}
	}
	
    
    public double calculateAdditionalPriceOfServices(List<Integer> orderedAdditionalServices) {
    	double additionalServicePrice = 0;
        for (int i = 0; i < orderedAdditionalServices.size(); i++) {
        	additionalServicePrice = additionalServicePrice + this.additionalServiceDao.getById(orderedAdditionalServices.get(i)).getDailyPrice();
        }
		return additionalServicePrice;
    }

}
