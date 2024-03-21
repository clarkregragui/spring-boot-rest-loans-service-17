package com.riggy.example.loans.controller.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import com.riggy.example.loans.data.CustomerRepository;
import com.riggy.example.loans.data.LoanProductRepository;
import com.riggy.example.loans.model.dto.CustomerDTO;
import com.riggy.example.loans.model.dto.LoanDTO;
import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerLoansRestTest {
	
	
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Autowired
    CustomerRepository customerRepository;
    
    @Autowired
    LoanProductRepository loanProductRepository;
    
        
    
    @Test
    @Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    public void postSubmitCustLoanTest() {
    	
    	
        //GIVEN
        LoanDTO loanDto = new LoanDTO();
        loanDto.setLoanProductId(1L);
        
        loanDto.setOpeningBalance(BigDecimal.valueOf(100000d));
        loanDto.setCurrentBalance(BigDecimal.valueOf(100000d));
        loanDto.setPaymentAmount(BigDecimal.valueOf(500d));
        loanDto.setPaymentDate(LocalDate.now().plusDays(1L));
        loanDto.setRate(BigDecimal.valueOf(6.25d));
        
        Set<LoanDTO> loans = new HashSet<>();
        loans.add(loanDto);
        
    	CustomerDTO customerDto = new CustomerDTO("Naseem Regragui", LocalDate.of(1978, 1, 1), "Mr.", loans);


        //WHEN
    	HttpHeaders headers = new HttpHeaders();

    	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

    	HttpEntity<CustomerDTO> request = new HttpEntity<CustomerDTO>(customerDto, headers);

    	ResponseEntity<Void> postResponse = restTemplate.exchange("/submitCustLoan",
    			HttpMethod.POST, request, new ParameterizedTypeReference<Void>() {
    	});
    	
    	
    	//THEN
    	assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    	
    	
    	Customer expectedCustomer = new Customer();
    	expectedCustomer.setTitle(customerDto.getTitle());
    	expectedCustomer.setName(customerDto.getName());
    	expectedCustomer.setDob(customerDto.getDob());
    	
    	Loan expectedLoan = new Loan();
    	expectedLoan.setCustomer(expectedCustomer);
    	expectedLoan.setOpeningBalance(BigDecimal.valueOf(100000d).setScale(1));
    	expectedLoan.setCurrentBalance(BigDecimal.valueOf(100000d).setScale(1));
    	expectedLoan.setPaymentAmount(BigDecimal.valueOf(500d).setScale(1));
    	expectedLoan.setPaymentDate(LocalDate.now().plusDays(1L));
    	expectedLoan.setRate(BigDecimal.valueOf(6.25d));
    	
    	LoanProduct loanProduct = loanProductRepository.findById(1L).orElseThrow();
    	expectedLoan.setLoanProduct(loanProduct);
    	
        Set<Loan> expectedLoans = new HashSet<>();
        expectedLoans.add(expectedLoan);
        expectedCustomer.setLoans(expectedLoans);
        
    	
    	Customer savedCustomer = customerRepository.findByNameAndDob(customerDto.getName(), customerDto.getDob());
    	
    	assertThat(savedCustomer.getLoans().size()).isEqualTo(1);
        
        Loan savedLoan = savedCustomer.getLoans().iterator().next();
        
        assertThat(savedLoan.getLoanId()).isGreaterThan(0L);
        
        savedLoan.setLoanId(null);
        savedCustomer.setCustomerId(null);
        
        assertThat(savedLoan).isEqualTo(expectedLoan);
        assertThat(savedCustomer).isEqualTo(expectedCustomer);

    	
    }

    
    @Test
    public void postValidationMessagingOnTest() {
    	
    	CustomerDTO customerDto = new CustomerDTO("Naseem Regragui", LocalDate.of(1978, 1, 1), "Mr.", null);
    	
    	ResponseEntity<String> postResponse = restTemplate.postForEntity("/submitCustLoan", customerDto, String.class);
    	
    	assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    	assertThat(postResponse.getBody()).contains("Loan required");
    }

}
