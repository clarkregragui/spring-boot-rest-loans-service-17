package com.riggy.example.loans.model.entity;

import java.io.Serializable;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "LOAN_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoanProduct implements Serializable {
	
	private static final long serialVersionUID = -4352200570891140589L;

	@Id
    @Column(name = "LOAN_PRODUCT_ID", unique = true, nullable = false, length = 3)
	private Long loanProductId;
	
    @Column(name = "LOAN_PRODUCT_NAME", unique = true, nullable = false, length = 50)
	private String loanProductName;
	
    @Column(name = "VARIABLE_INTEREST_RATE", unique = false, nullable = false)
	private Boolean variableInterestRate;
}
