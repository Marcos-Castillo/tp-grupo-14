package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LoanDto {
    private Long id;
    private Double amount;
    private Double interest;
    private Long dues;
    private LocalDateTime date;
    private StatusLoan status;
    private Account account;
}
