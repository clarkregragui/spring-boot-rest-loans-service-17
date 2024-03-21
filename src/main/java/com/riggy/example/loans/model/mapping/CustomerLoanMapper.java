package com.riggy.example.loans.model.mapping;

import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.model.entity.Customer;

public interface CustomerLoanMapper {

	
	public Customer customerDtoToCustomer(CustomerDTO customerDto);
	
	public CustomerDTO customerToCustomerDto(Customer customer);
}
