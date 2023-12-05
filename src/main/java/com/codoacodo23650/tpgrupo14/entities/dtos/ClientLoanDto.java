package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class ClientLoanDto {
    private Long id;
    private Long dues;
    private double dueAmmount;
    private Long pendDues;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private StatusLoan status;
    private Account account;
    private Loan loan;
}
