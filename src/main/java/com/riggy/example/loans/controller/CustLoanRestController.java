package com.riggy.example.loans.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.service.CustomerLoanService;

@RestController
@Validated
public class CustLoanRestController {

	@Autowired
	private CustomerLoanService customerLoanService;
	
	
	@PostMapping(value = "/submitCustLoan")
	public ResponseEntity<Void> submitCustomerLoan(@Valid @RequestBody CustomerDTO customerDto) {
		
			customerLoanService.submitCustomerLoan(customerDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
			
	}
}
