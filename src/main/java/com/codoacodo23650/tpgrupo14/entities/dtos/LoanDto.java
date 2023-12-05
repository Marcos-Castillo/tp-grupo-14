package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {
    private Long id;
    private Double amount;
    private Double interest;
    private Long dues;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private StatusLoan status;
    private Account account;
    private LocalDateTime date;
}
