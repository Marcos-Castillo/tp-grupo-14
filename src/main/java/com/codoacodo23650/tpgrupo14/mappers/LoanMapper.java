package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Loan;

import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoanMapper {

    public LoanDto loanToDto(Loan loan) {
        return LoanDto.builder()
                .id(loan.getId())
                .amount(loan.getAmount())
                .interest(loan.getInterest())
                .status(loan.getStatus())
                .dues(loan.getDues())
                .date(loan.getDate())
                .account(loan.getAccount())
                .build();
    }

    public Loan dtoToLoan(LoanDto dto) {
        return new Loan(
                (dto.getId()),
                (dto.getAmount()),
                (dto.getInterest()),
                (dto.getDues()),
                (dto.getDate()),
                (dto.getStatus()),
                (dto.getAccount())
                );
    }
}
