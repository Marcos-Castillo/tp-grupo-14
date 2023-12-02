package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoanMapper {
    public Loan dtoToLoan(LoanDto dto) {
        return Loan.builder().id(dto.getId()).amount(dto.getAmount()).date(dto.getDate()).dues(dto.getDues()).interest(dto.getInterest()).status(dto.getStatus()).build();
    }

    public loanToDto(Loan loan){
        return LoanDto.build
    }
}
