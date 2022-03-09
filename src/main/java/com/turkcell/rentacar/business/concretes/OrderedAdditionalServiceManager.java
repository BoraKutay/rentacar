package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceByIdDto;
import com.turkcell.rentacar.business.dtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService{

	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;
	
	
	@Autowired
	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService) {
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<OrderedAdditionalServiceListDto>> getAll() {
        List<OrderedAdditionalService> result = this.orderedAdditionalServiceDao.findAll();
        List<OrderedAdditionalServiceListDto> response = result.stream()
                .map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<OrderedAdditionalServiceListDto>>(response, "Ordered Additional Services are listed successfully.");
	}

	@Override
	public Result add(CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest)
			throws BusinessException {
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(createOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult("Ordered Additional Service is added.");
	}

	@Override
	public DataResult<OrderedAdditionalServiceByIdDto> getById(int id) throws BusinessException {
		
		checkIfOrderedAdditionalServiceExists(id);
		OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);

        OrderedAdditionalServiceByIdDto response = this.modelMapperService.forDto().map(orderedAdditionalService, OrderedAdditionalServiceByIdDto.class);
		return new SuccessDataResult<OrderedAdditionalServiceByIdDto>(response,"Ordered Additional Service is found by id: " + id);
	}

	@Override
	public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest)
			throws BusinessException {
		checkIfOrderedAdditionalServiceExists(updateOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(updateOrderedAdditionalServiceRequest, OrderedAdditionalService.class);
		
		this.orderedAdditionalServiceDao.save(orderedAdditionalService);
		return new SuccessResult("Ordered Additional Service is updated.");
	}

	@Override
	public Result deleteById(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest)
			throws BusinessException {
		checkIfOrderedAdditionalServiceExists(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		this.orderedAdditionalServiceDao.deleteById(deleteOrderedAdditionalServiceRequest.getOrderedAdditionalServiceId());
		return new SuccessResult("Ordered Additional Service is deleted.");
	}
	
    private boolean checkIfOrderedAdditionalServiceExists(int id) throws BusinessException {
    	if(orderedAdditionalServiceDao.existsById(id) == false) {
    		throw new BusinessException("Ordered Additional service does not exist by id:" + id);
    	}
		return true;
    }

}
