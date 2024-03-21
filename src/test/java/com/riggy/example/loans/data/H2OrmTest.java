package com.riggy.example.loans.data;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashSet;
import java.util.Set;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import com.riggy.example.loans.model.entity.Customer;
import com.riggy.example.loans.model.entity.Loan;
import com.riggy.example.loans.model.entity.LoanProduct;

@ActiveProfiles("test")
@DataJpaTest
public class H2OrmTest {


    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private LoanProductRepository loanProductRepository;
    
    @Autowired
    private LoanRepository loanRepository;

    
    @Test
    @DirtiesContext
    @Sql("classpath:sql/schema.sql")
    public void saveCustomer() {
    	
        Customer naseem = new Customer(null, "Naseem Regragui", LocalDate.now(), null, null);
        naseem = customerRepository.save(naseem);

        Customer found = customerRepository.findByNameAndDob(naseem.getName(), naseem.getDob());

        assertThat(found).isEqualTo(naseem);
    }
    
    @Test
    @DirtiesContext
    @Sql("classpath:sql/schema.sql")
    public void saveLoanProduct() {
    	
        Customer naseem = new Customer(null, "Naseem Regragui", LocalDate.now(), null, null);
        naseem = customerRepository.save(naseem);

        Customer found = customerRepository.findByNameAndDob(naseem.getName(), naseem.getDob());

        assertThat(found).isEqualTo(naseem);
    }
    
    @Test
    @DirtiesContext
    @Sql(scripts = {"classpath:sql/schema.sql", "classpath:sql/data.sql"})
    public void saveCustomerWithLoan() {
    	
    	LoanProduct loanProduct = loanProductRepository.findById(2L).orElseThrow();
    	
        Customer customer = new Customer(null, "Naseem Regragui", LocalDate.now(), null, null);
                
        Loan loan = new Loan();
        
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

        customer = customerRepository.save(customer);
        
        Customer found = customerRepository.findByNameAndDob(customer.getName(), customer.getDob());

        assertThat(customer).isEqualTo(found);
        assertThat(found.getLoans().size()).isEqualTo(1);
        assertThat(found.getLoans()).isEqualTo(loans);
        
    }

    /*
     *  Trigger not accepted by H2DB
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

