package com.riggy.example.loans.model.mapping;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.model.dto.LoanDTO;
import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;



@Component
public class CustomerLoanMapperImpl implements CustomerLoanMapper {
	
	
	private ModelMapper modelMapper;
	
	@Autowired
	public CustomerLoanMapperImpl(ModelMapper modelMapper) {

		modelMapper.addConverter(custDtoToCustConverter);
		this.modelMapper = modelMapper;
	}
	
	
	
    private Converter<CustomerDTO, Customer> custDtoToCustConverter = 
    		new AbstractConverter<CustomerDTO, Customer>() {

  	   @Override
  	   public Customer convert(CustomerDTO source) {
  		   
  		   Customer destination = new Customer();
  		   destination.setTitle(source.getTitle());
  		   destination.setName(source.getName());
  		   destination.setDob(source.getDob());
  		   
  		   source.getLoans().stream()
  		   		.map(loanDto -> mapLoanDto(loanDto))
  		   		.forEach(loan -> destination.addLoan(loan));
  		   
  		  return destination;

  	   }
  	   
  	   private Loan mapLoanDto(LoanDTO loanDto) {
  		   Loan loan = new Loan();
  		   loan.setCurrentBalance(loanDto.getCurrentBalance());
  		   loan.setOpeningBalance(loanDto.getOpeningBalance());
  		   loan.setPaymentAmount(loanDto.getPaymentAmount());
  		   loan.setRate(loanDto.getRate());
  		   loan.setPaymentDate(loanDto.getPaymentDate());
  		   loan.setLoanProduct(new LoanProduct(loanDto.getLoanProductId(),null,null));
  		   return loan;
  	   }
  };
  

	@Override
	public Customer customerDtoToCustomer(CustomerDTO customerDto) {
		
		return modelMapper.map(customerDto, Customer.class);
		
	}


	@Override
	public CustomerDTO customerToCustomerDto(Customer customer) {
		
		return modelMapper.map(customer, CustomerDTO.class);

	}
}
