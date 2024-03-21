package com.riggy.example.loans.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.riggy.example.loans.model.entity.Loan;

@Repository
public interface LoanRepository extends CrudRepository<Loan,Long> {

}