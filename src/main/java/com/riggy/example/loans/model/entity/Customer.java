package com.riggy.example.loans.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CUSTOMER", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME", "DOB"})})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer implements Serializable {


	private static final long serialVersionUID = -5974392295510747696L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUSTOMER_ID", unique = true, nullable = false, length = 3)
    private Long customerId;

    @Column(name = "NAME", unique = false, nullable = false, length = 50)
    private String name;
    
    @Column(name = "DOB", columnDefinition = "DATE", unique = false, nullable = false)
	private LocalDate dob;
    
    @Column(name = "TITLE", unique = false, nullable = true, length = 10)
	private String title;
    
    @OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, 
            mappedBy="customer",
            fetch = FetchType.EAGER)
	private Set<Loan> loans;
    
    
    public void addLoan(Loan loan) {
    	if(loans == null) {
    		loans = new HashSet<>();
    	}
    	loan.setCustomer(this);
    	loans.add(loan);
    }
    
    
	@Override
	public int hashCode() {
		return Objects.hash(customerId, dob, name, title);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(dob, other.dob)
				&& Objects.equals(name, other.name)
				&& Objects.equals(title, other.title);
	}

}
