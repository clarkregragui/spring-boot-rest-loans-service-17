package com.riggy.example.loans.model.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO implements Serializable {

	private static final long serialVersionUID = -6682099118837047772L;
	
    @Positive(message = "{loandto.prodid.positive}")
    @NotNull(message = "{loandto.prodid.required}")
    private Long loanProductId;
    
    @DecimalMax(value = "99999999.99", message = "{loandto.money.max}")
    @DecimalMin(value = "0.0", message = "{loandto.decimal.min}")
    @Digits(integer=8, fraction=2, message="{loandto.money.max}")
    private BigDecimal openingBalance;
    
    @DecimalMax(value = "99999999.99", message = "{loandto.money.max}")
    @DecimalMin(value = "0.0", message = "{loandto.decimal.min}")
    @Digits(integer=8, fraction=2, message="{loandto.money.max}")
    private BigDecimal currentBalance;
    
    @DecimalMax(value = "99999999.99", message = "{loandto.money.max}")
    @DecimalMin(value = "0.0", message = "{loandto.decimal.min}")
    @Digits(integer=8, fraction=2, message="{loandto.money.max}")
    private BigDecimal paymentAmount;
    
    @Future(message = "{loandto.payment.future}")
    private LocalDate paymentDate;
    
    @DecimalMax(value = "99999999.99", message = "{loandto.money.max}")
    @DecimalMin(value = "0.0", message = "{loandto.decimal.min}")
    @Digits(integer=8, fraction=2, message="{loandto.money.max}")
    private BigDecimal rate;
}
