package com.turkcell.rentacar.core.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentacar.core.utilities.results.ErrorResult;

@RestControllerAdvice
public class GlobalException {
	
	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException argumentNotValidException){
		Map<String, String> validationErrors = new HashMap<String, String>();
		for (FieldError fieldError : argumentNotValidException.getBindingResult().getFieldErrors()) {
			validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors,"Validation.Error");
		return errorDataResult;
	}


	@ExceptionHandler
	@ResponseStatus(code=HttpStatus.BAD_REQUEST)
	public ErrorResult businessValidationExceptions(BusinessException businessException){
		ErrorResult errorResult = new ErrorResult(businessException.getMessage());
		return errorResult;
	}
}
