package com.riggy.example.loans.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.oracle.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;

@ActiveProfiles("e2e")
@SpringBootTest
@Testcontainers
public class OracleContainerTest {

	@Container
	@ServiceConnection
	static OracleContainer oracleFreeContainer =
			new OracleContainer(DockerImageName.parse("gvenzl/oracle-free:slim-faststart"))
				.withInitScript("sql/schema-data.sql")
				.withStartupTimeout(Duration.ofSeconds(180000L));

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private LoanProductRepository loanProductRepository;

	
	@Test
	public void findCustomer() {

		Set<Loan> loans = new HashSet<>();
		Customer customer = new Customer(null, "John Smith", LocalDate.of(1978, 1, 1), "Mr", loans);
		customer = customerRepository.save(customer);

		Customer found = customerRepository.findByNameAndDob(customer.getName(), customer.getDob());

		assertThat(found).isEqualTo(customer);
	}
	
    @Test
    public void saveCustomerWithLoan() {
    	    	
    	LoanProduct loanProduct = loanProductRepository.findById(2L).orElseThrow();
    	
		Customer customer = new Customer(null, "John Doe", LocalDate.of(1978, 1, 2), "Mr", null);
                
        Loan loan = new Loan();
        
        loan.setCustomer(customer);
        loan.setLoanProduct(loanProduct);
        
        loan.setOpeningBalance(BigDecimal.valueOf(100000d).setScale(0));
        loan.setCurrentBalance(BigDecimal.valueOf(100000d).setScale(0));
        loan.setPaymentAmount(BigDecimal.valueOf(500d).setScale(0));
        loan.setPaymentDate(LocalDate.now());
        loan.setRate(BigDecimal.valueOf(6.25d));
        
        Set<Loan> loans = new HashSet<>();
        loans.add(loan);
        customer.setLoans(loans);

        customer = customerRepository.save(customer);
        
        Customer found = customerRepository.findByNameAndDob(customer.getName(), customer.getDob());

        assertThat(found).isEqualTo(customer);
        assertThat(found.getLoans().size()).isEqualTo(1);
        assertThat(found.getLoans()).isEqualTo(loans);
        
    }
    
    /*
     *  Trigger PL/SQL syntax not accepted by TestContainer ScriptRunner.
     */
//    @Test
//    @DirtiesContext
//    @Sql("classpath:sql/schema.sql")
//    public void testRateTrigger() {
//    	
//    	LoanProduct loanProduct = new LoanProduct(null, "Variable Rate Product", true);
//    	
//    	loanProduct = loanProductRepository.save(loanProduct);
//    	
//        Customer customer = new Customer(null, "Naseem Regragui", LocalDate.now(), null, null);
//                
//        Loan loan = new Loan();
//        
//        loan.setCustomer(customer);
//        loan.setLoanProduct(loanProduct);
//        
//        loan.setOpeningBalance(BigDecimal.valueOf(100000d));
//        loan.setCurrentBalance(BigDecimal.valueOf(100000d));
//        loan.setPaymentAmount(BigDecimal.valueOf(500d));
//        loan.setPaymentDate(LocalDate.now());
//        //loan.setRate(BigDecimal.valueOf(7.25d));
//        
//        Set<Loan> loans = new HashSet<>();
//        loans.add(loan);
//        customer.setLoans(loans);
//
//        customer = customerRepository.save(customer);
//        
//        Customer found = customerRepository.findByNameAndDob(customer.getName(), customer.getDob());
//
//        assertThat(found.getLoans().iterator().next().getRate()).isEqualTo(BigDecimal.valueOf(5.25d));
//        
//    }
}
