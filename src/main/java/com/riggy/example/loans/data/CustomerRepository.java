package com.riggy.example.loans.data;

import java.time.LocalDate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.riggy.example.loans.model.entity.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {

	public Customer findByNameAndDob(String name, LocalDate dob);

}
