package com.riggy.example.loans.controller;

import java.util.HashMap;
import java.util.Map;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.riggy.example.loans.service.NoSuchProductException;

@ControllerAdvice
public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {
	

	  @ExceptionHandler(ConstraintViolationException.class)
	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ResponseBody
	  public Map<String, String> onConstraintValidationException(
	      ConstraintViolationException e) {
		  Map<String, String> errors = new HashMap<>();
	    for (ConstraintViolation violation : e.getConstraintViolations()) {
	      errors.put(violation.getPropertyPath().toString(), violation.getMessage());
	    }
	    return errors;
	  }

	  @ResponseStatus(HttpStatus.BAD_REQUEST)
	  @ExceptionHandler(NoSuchProductException.class)
	  @ResponseBody
	  public Map<String, String> handleNoSuchProductExceptions(
			  NoSuchProductException ex) {
		  
	      Map<String, String> errors = new HashMap<>();
          errors.put("loanProductId", ex.getMessage());

	      return errors;
	  }
	  	  
	  
		@Override
		protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		    Map<String, String> errors = new HashMap<>();
		    ex.getBindingResult().getAllErrors().forEach((error) -> {
		        String fieldName = ((FieldError) error).getField();
		        String errorMessage = error.getDefaultMessage();
		        errors.put(fieldName, errorMessage);
		    });
		    
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
}
