package com.riggy.example.loans.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOAN")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan implements Serializable {
	
	
	private static final long serialVersionUID = -4462016026394128421L;

	@ManyToOne
    @JoinColumn(name="CUSTOMER_ID", nullable=false, referencedColumnName="CUSTOMER_ID")
	private Customer customer;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOAN_ID", unique = true, nullable = false, length = 3)
	private Long loanId;
    
    @ManyToOne(optional=false) 
    @JoinColumn(name="LOAN_PRODUCT_ID", nullable=false, updatable=false, referencedColumnName="LOAN_PRODUCT_ID")
    private LoanProduct loanProduct;
    
    @Column(name="OPENING_BALANCE", nullable=false, precision=10, scale=2)
    private BigDecimal openingBalance;
    
    @Column(name="CURRENT_BALANCE", nullable=false, precision=10, scale=2)
    private BigDecimal currentBalance;
    
    @Column(name="PAYMENT_AMOUNT", nullable=false, precision=10, scale=2)
    private BigDecimal paymentAmount;
    
    @Column(name = "PAYMENT_DATE", columnDefinition = "DATE", unique = false, nullable = false)
    private LocalDate paymentDate;
    
    @Column(name="RATE", nullable=false, precision=10, scale=2)
    private BigDecimal rate;

	@Override
	public int hashCode() {
		return Objects.hash(currentBalance, loanId, openingBalance, paymentAmount, paymentDate, rate);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Loan other = (Loan) obj;
		return Objects.equals(currentBalance, other.currentBalance)
				&& Objects.equals(loanId, other.loanId)
				&& Objects.equals(customer.getCustomerId(), other.getCustomer().getCustomerId())
				&& Objects.equals(loanProduct, other.loanProduct)
				&& Objects.equals(openingBalance, other.openingBalance)
				&& Objects.equals(paymentAmount, other.paymentAmount) && Objects.equals(paymentDate, other.paymentDate)
				&& Objects.equals(rate, other.rate);
	}

	@Override
	public String toString() {
		return "Loan [loanId=" + loanId + ", loanProduct=" + loanProduct + ", openingBalance=" + openingBalance
				+ ", currentBalance=" + currentBalance + ", paymentAmount=" + paymentAmount + ", paymentDate="
				+ paymentDate + ", rate=" + rate + "]";
	}
}
