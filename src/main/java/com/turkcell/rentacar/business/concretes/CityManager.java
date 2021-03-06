package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.cityDtos.CityByIdDto;
import com.turkcell.rentacar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.city.CityNameNotUniqueException;
import com.turkcell.rentacar.core.exceptions.city.CityNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CityDao;
import com.turkcell.rentacar.entities.concretes.City;


@Service
public class CityManager implements CityService {

	private CityDao cityDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CityListDto>> getAll() {

        List<City> result = this.cityDao.findAll();
        
        List<CityListDto> response = result.stream()
                .map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CITIES + BusinessMessages.LISTED);
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) throws BusinessException {
		
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);
		
		checkIfCityNameIsUnique(createCityRequest.getCityName());
		
		this.cityDao.save(city);
		
		return new SuccessResult(BusinessMessages.CITY + BusinessMessages.ADDED);
	}

	@Override
	public DataResult<CityByIdDto> getById(int id) throws BusinessException {
		
		checkIfCityExists(id);
		
		City city = this.cityDao.getById(id);
		
		CityByIdDto response = this.modelMapperService.forDto().map(city, CityByIdDto.class);
		
		return new SuccessDataResult<CityByIdDto>(response,BusinessMessages.CITY + BusinessMessages.GET_BY_ID + id);
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {
		
		checkIfCityExists(updateCityRequest.getCityId());
		
		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
		
		checkIfCityNameIsUnique(updateCityRequest.getCityName());

        this.cityDao.save(city);

        return new SuccessResult(BusinessMessages.CITY + BusinessMessages.UPDATED);
	}

	@Override
	public Result deleteById(DeleteCityRequest deleteCityRequest) throws BusinessException {
		
		checkIfCityExists(deleteCityRequest.getCityId());
		
		this.cityDao.deleteById(deleteCityRequest.getCityId());
		
		return new SuccessResult(BusinessMessages.CITY + BusinessMessages.DELETED);
	}
	
	public boolean checkIfCityExists(int id) throws BusinessException{
		
		if(cityDao.existsById(id) == false) {
			throw new CityNotFoundException(BusinessMessages.CITY + BusinessMessages.DOES_NOT_EXISTS + id);
		}
		return true;
	}
	
    private boolean checkIfCityNameIsUnique(String cityName) throws BusinessException {

        for (CityListDto cityElement : this.getAll().getData()) {
            if (cityElement.getCityName().equalsIgnoreCase(cityName)) {
                throw new CityNameNotUniqueException(BusinessMessages.NOT_UNIQUE + cityName);
            }
        }

        return true;

    }

}
