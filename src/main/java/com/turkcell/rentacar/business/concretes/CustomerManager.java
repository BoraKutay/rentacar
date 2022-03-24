package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentacar.entities.concretes.Customer;

public class CustomerManager implements CustomerService{

	private CustomerDao customerDao;
	
	
	public CustomerManager(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	@Override
	public Customer getCustomerById(int id) {

		return this.customerDao.getById(id);
	}


	@Override
	public boolean checkIfCustomerExists(int id) throws BusinessException {
    	if(customerDao.existsById(id) == false) {
    		throw new BusinessException(BusinessMessages.CUSTOMER + BusinessMessages.DOES_NOT_EXISTS + id);
    	}
		return true;
	}

}
