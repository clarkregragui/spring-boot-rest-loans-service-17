package com.riggy.example.loans.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO implements Serializable {

	

	private static final long serialVersionUID = 879244033829086625L;
    
    
    
    @Size(max = 50, message = "{custdto.name.length}")
    @NotBlank(message = "{custdto.name.required}")
    private String name;
    
    @Past(message = "{custdto.dob.past}")
	private LocalDate dob;

    @Size(max = 10, message = "{custdto.title.length}")
	private String title;
    
    @Valid
    @NotEmpty(message = "{custdto.loans.required}")
	private Set<LoanDTO> loans;
}
