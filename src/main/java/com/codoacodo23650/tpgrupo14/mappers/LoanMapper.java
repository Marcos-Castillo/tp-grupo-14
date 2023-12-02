package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LoanMapper {
    public Loan dtoToLoan(LoanDto dto){
        Loan loan = new Loan();
        loan.setAmount(dto.getAmount());
        loan.setInterest(dto.getInterest());
        loan.setDues(dto.getDues());
        loan.setCreated_at(dto.getCreated_at());
        loan.setUpdated_at(dto.getUpdated_at());
        return loan;
    }

    public LoanDto loanToDto(Loan loan){
        LoanDto dto = new LoanDto();
        dto.setId(loan.getId());
        dto.setAmount(loan.getAmount());
        dto.setInterest(loan.getInterest());
        dto.setDues(loan.getDues());
        dto.setCreated_at(loan.getCreated_at());
        dto.setUpdated_at(loan.getUpdated_at());
        return dto;
    }
}
