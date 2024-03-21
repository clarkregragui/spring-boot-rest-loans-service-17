package com.riggy.example.loans.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.riggy.example.loans.data.CustomerRepository;
import com.riggy.example.loans.data.LoanProductRepository;
import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;
import com.riggy.example.loans.model.mapping.CustomerLoanMapper;



@Service
public class CustomerLoanServiceImpl implements CustomerLoanService {

	@Autowired
	private CustomerLoanMapper modelMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private LoanProductRepository cachedLoanProductRepository;
	

	@Override
	public void submitCustomerLoan(CustomerDTO customerDto) {

		Customer customer = modelMapper.customerDtoToCustomer(customerDto);
		
		validateAndEnrichLoanProduct(customer.getLoans());
		   
		Customer existingCustomer = customerRepository.findByNameAndDob(customer.getName(), customer.getDob());
		
		if(Objects.isNull(existingCustomer)) {
			
			customer.setCustomerId(null);
			customerRepository.save(customer);
			
		} else {
			Set<Loan> newLoans  = customer.getLoans();
			newLoans.stream()
				.forEach(l -> l.setCustomer(existingCustomer));
			
			existingCustomer.getLoans().addAll(newLoans);
			customerRepository.save(existingCustomer);
		}
	}


	private void validateAndEnrichLoanProduct(Set<Loan> loans) {
		
		loans.stream().forEach(loan -> {
			Optional<LoanProduct> opLoanProduct = cachedLoanProductRepository.findById(loan.getLoanProduct().getLoanProductId());
			   loan.setLoanProduct(opLoanProduct.orElseThrow(
					   () -> new NoSuchProductException("Unrecognized product ID: " + loan.getLoanProduct().getLoanProductId()))
					   );
		});

	}
}
