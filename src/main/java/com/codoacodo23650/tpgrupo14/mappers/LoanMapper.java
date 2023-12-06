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
                .duesAmount((loan.getDuesAmount()))
                .date(loan.getDate())
                .account(loan.getAccount())
                .created_at(loan.getCreated_at())
                .updated_at(loan.getUpdated_at())
                .build();
    }

    public Loan dtoToLoan(LoanDto dto) {
        return new Loan(
                (dto.getId()),
                (dto.getAmount()),
                (dto.getInterest()),
                (dto.getDues()),
                (dto.getDuesAmount()),
                (dto.getDate()),
                (dto.getStatus()),
                (dto.getAccount()),
                (dto.getCreated_at()),
                (dto.getUpdated_at())
                );
    }
}
