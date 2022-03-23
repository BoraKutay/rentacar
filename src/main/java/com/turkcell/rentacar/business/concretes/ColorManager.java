package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperService modelMapperService;
    
    @Autowired
    public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
    public DataResult<List<ColorListDto>> getAll() {

        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream()
                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<ColorListDto>>(response, BusinessMessages.COLORS + BusinessMessages.LIST);

    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
    	
        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

        checkIfColorNameIsUnique(color.getColorName());

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.COLOR + BusinessMessages.ADD);
    }

    @Override
    public DataResult<ColorByIdDto> getById(int colorId) throws BusinessException {
    	
    	checkIfColorExists(colorId);
    	
        Color color = this.colorDao.getById(colorId);

        ColorByIdDto response = this.modelMapperService.forDto().map(color, ColorByIdDto.class);

        return new SuccessDataResult<ColorByIdDto>(response,BusinessMessages.COLOR + BusinessMessages.GET_BY_ID + colorId);
    }


    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
    	
    	checkIfColorExists(updateColorRequest.getColorId());
    	checkIfColorNameIsUnique(updateColorRequest.getColorName());
    	
        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.COLOR + BusinessMessages.UPDATE);
    }

    @Override
    public Result deleteById(DeleteColorRequest deleteColorRequest) throws BusinessException {
    	
    	checkIfColorExists(deleteColorRequest.getColorId());
    	
        this.colorDao.deleteById(deleteColorRequest.getColorId());
        
        return new SuccessResult(BusinessMessages.COLOR + BusinessMessages.DELETE);

    }

    private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {


        if (this.colorDao.existsByColorName(colorName)) {
      	
            throw new BusinessException(BusinessMessages.NOT_UNIQUE + BusinessMessages.COLOR);
            
        }


        return true;

    }
    
    private boolean checkIfColorExists(int id) throws BusinessException {
    	
    	if(colorDao.existsById(id) == false) {
    		throw new BusinessException(BusinessMessages.COLOR + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
    }

}