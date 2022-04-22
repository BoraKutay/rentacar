package com.turkcell.rentacar.core.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException argumentNotValidException){
		Map<String, String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors,"Validation.Error");
		return errorDataResult;
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleDataException (DataIntegrityViolationException dataIntegrityViolationException){
		
		Map<String, String> validationErrors = new HashMap<String, String>();
		validationErrors.put(BusinessMessages.DATA_INTEGRITY_VIOLATION, "Data Exception");

		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors, "Data Exception");
		return errorDataResult;
	}


	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorResult businessValidationExceptions(BusinessException businessException){
		ErrorResult errorResult = new ErrorResult(businessException.getMessage());
		return errorResult;
	}

	@ExceptionHandler(InvalidFormatException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public ErrorResult invalidTypeExceptions(InvalidFormatException invalidFormatException ){
		ErrorResult errorResult = new ErrorResult(invalidFormatException.getMessage());
		return errorResult;
	}

	
}
