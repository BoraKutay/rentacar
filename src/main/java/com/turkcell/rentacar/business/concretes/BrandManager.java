package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.exceptions.brand.BrandNameNotUniqueException;
import com.turkcell.rentacar.core.exceptions.brand.BrandNotFoundException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {

    private BrandDao brandDao;
    private ModelMapperService modelMapperService;
    
    public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
    public DataResult<List<BrandListDto>> getAll() {
		
        List<Brand> result = this.brandDao.findAll();

        List<BrandListDto> response = result.stream()
                .map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<BrandListDto>>(response, BusinessMessages.BRANDS + BusinessMessages.LISTED);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {


        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        checkIfBrandNameIsUnique(brand.getBrandName());

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.BRAND + BusinessMessages.ADDED);

    }


    @Override
    public DataResult<BrandByIdDto> getById(int brandId) throws BusinessException {
    	
    	checkIfBrandExists(brandId);
    	
        Brand brand = this.brandDao.getById(brandId);

        BrandByIdDto response = this.modelMapperService.forDto().map(brand, BrandByIdDto.class);

        return new SuccessDataResult<BrandByIdDto>(response, BusinessMessages.BRAND + BusinessMessages.GET_BY_ID + brandId);
    }


    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
    	
    	checkIfBrandExists(updateBrandRequest.getBrandId());
    	checkIfBrandNameIsUnique(updateBrandRequest.getBrandName());
    	
        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        this.brandDao.save(brand);
        
        return new SuccessResult(BusinessMessages.BRAND + BusinessMessages.UPDATED);
    }

    @Override
    public Result deleteById(DeleteBrandRequest deleteBrandRequest) throws BusinessException {
    	
    	checkIfBrandExists(deleteBrandRequest.getBrandId());
    	
        this.brandDao.deleteById(deleteBrandRequest.getBrandId());
        
        return new SuccessResult(BusinessMessages.BRAND + BusinessMessages.DELETED);
    }

    private boolean checkIfBrandNameIsUnique(String brandName) throws BusinessException {

        if (this.brandDao.existsByBrandNameIgnoreCase(brandName)) {
        	
            throw new BrandNameNotUniqueException(BusinessMessages.NOT_UNIQUE + brandName);
        }

        return true;

    }
    
    private boolean checkIfBrandExists(int id) throws BusinessException {
    	
    	if(brandDao.existsById(id) == false) {
    		throw new BrandNotFoundException(BusinessMessages.BRAND + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
    }
    
    


}
