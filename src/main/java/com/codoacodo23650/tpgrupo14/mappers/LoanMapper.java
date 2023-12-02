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
                .dues(loan.getDues())
                .date(loan.getDate())
                .account(loan.getAccount())
                .build();
    }

    public Loan dtoToLoan(LoanDto dto) {
        return Loan.builder()
                .id(dto.getId())
                .amount(dto.getAmount())
                .interest(dto.getInterest())
                .dues(dto.getDues())
                .date(dto.getDate())
                .account(dto.getAccount())
                .build();
    }
}
