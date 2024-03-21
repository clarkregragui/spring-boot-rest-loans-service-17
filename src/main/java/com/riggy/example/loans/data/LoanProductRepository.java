package com.riggy.example.loans.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.riggy.example.loans.model.entity.LoanProduct;

@Repository
public interface LoanProductRepository extends CrudRepository<LoanProduct,Long> {
	

}