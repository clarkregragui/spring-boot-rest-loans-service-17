package com.riggy.example.loans.model.mapping;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.junit.jupiter.api.Test;
import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.model.dto.LoanDTO;
import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;

public class CustomerLoanMapperTest {
	
	
	private CustomerLoanMapper mapper = new CustomerLoanMapperImpl(new ModelMapper());
	

	@Test
	public void customerDtoToEntity() {
		
		
        //GIVEN    
        LoanDTO loanDto = new LoanDTO();
        loanDto.setLoanProductId(1L);
        
        loanDto.setOpeningBalance(BigDecimal.valueOf(100000d));
        loanDto.setCurrentBalance(BigDecimal.valueOf(100000d));
        loanDto.setPaymentAmount(BigDecimal.valueOf(500d));
        loanDto.setPaymentDate(LocalDate.now());
        loanDto.setRate(BigDecimal.valueOf(6.25d));
        
        Set<LoanDTO> loans = new HashSet<>();
        loans.add(loanDto);
        
    	CustomerDTO customerDto = new CustomerDTO("Naseem Regragui", LocalDate.of(1978, 1, 1), "Mr.", null);
        customerDto.setLoans(loans);
        
        
        //WHEN
        Customer customer = mapper.customerDtoToCustomer(customerDto);
        
        
        //THEN
        assertThat(customer.getName()).isEqualTo(customerDto.getName());
        assertThat(customer.getDob()).isEqualTo(customerDto.getDob());
        assertThat(customer.getTitle()).isEqualTo(customerDto.getTitle());
        assertThat(customer.getCustomerId()).isEqualTo(null);

        
        assertThat(customer.getLoans().size()).isEqualTo(customerDto.getLoans().size());

        
        Loan loan = customer.getLoans().iterator().next();  
        
        assertThat(loan.getCustomer()).isEqualTo(customer);
        assertThat(loan.getCurrentBalance()).isEqualTo(loanDto.getCurrentBalance());
        assertThat(loan.getOpeningBalance()).isEqualTo(loanDto.getOpeningBalance());
        assertThat(loan.getPaymentAmount()).isEqualTo(loanDto.getPaymentAmount());
        assertThat(loan.getPaymentDate()).isEqualTo(loanDto.getPaymentDate());
        assertThat(loan.getRate()).isEqualTo(loanDto.getRate());
        assertThat(loan.getLoanProduct().getLoanProductId()).isEqualTo(loanDto.getLoanProductId());
        assertThat(loan.getLoanId()).isEqualTo(null);

	}
	
	@Test
	public void entityToCustomerDto() {
		
		//GIVEN
    	LoanProduct loanProduct = new LoanProduct(2L, "Variable Rate Product", true);
        Loan loan = new Loan();	
        Customer customer = new Customer(null, "Naseem Regragui", LocalDate.now(), null, null);
        
        loan.setCustomer(customer);
        loan.setLoanProduct(loanProduct);
        
        loan.setOpeningBalance(BigDecimal.valueOf(100000d));
        loan.setCurrentBalance(BigDecimal.valueOf(100000d));
        loan.setPaymentAmount(BigDecimal.valueOf(500d));
        loan.setPaymentDate(LocalDate.now());
        loan.setRate(BigDecimal.valueOf(6.25d));
        
        Set<Loan> loans = new HashSet<>();
        loans.add(loan);
        customer.setLoans(loans);
        
        //WHEN
        CustomerDTO customerDto = mapper.customerToCustomerDto(customer);
        
        
        //THEN
        assertThat(customerDto.getTitle()).isEqualTo(customer.getTitle());
        assertThat(customerDto.getName()).isEqualTo(customer.getName());
        assertThat(customerDto.getDob()).isEqualTo(customer.getDob());
        
        
        assertThat(customerDto.getLoans().size()).isEqualTo(customer.getLoans().size());
        
        LoanDTO loanDto = customerDto.getLoans().iterator().next();

        assertThat(loanDto.getCurrentBalance()).isEqualTo(loan.getCurrentBalance());
        assertThat(loanDto.getOpeningBalance()).isEqualTo(loan.getOpeningBalance());
        assertThat(loanDto.getPaymentAmount()).isEqualTo(loan.getPaymentAmount());
        assertThat(loanDto.getRate()).isEqualTo(loan.getRate());
        assertThat(loanDto.getPaymentDate()).isEqualTo(loan.getPaymentDate());
        assertThat(loanDto.getLoanProductId()).isEqualTo(loan.getLoanProduct().getLoanProductId());
	}
}
