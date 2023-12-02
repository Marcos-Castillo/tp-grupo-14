package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.ClientLoan;
import com.codoacodo23650.tpgrupo14.entities.dtos.ClientLoanDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ClientLoanMapper {
    public ClientLoan dtoToClientLoan(ClientLoanDto dto){
        ClientLoan clientLoan = new ClientLoan();
        clientLoan.setDues(dto.getDues());
        clientLoan.setPendDues(dto.getPendDues());
        clientLoan.setStatus(dto.getStatus());
        clientLoan.setCreated_at(dto.getCreated_at());
        clientLoan.setUpdated_at(dto.getUpdated_at());
        clientLoan.setAccount(dto.getAccount());
        clientLoan.setLoan(dto.getLoan());
        return clientLoan;
    }

    public ClientLoanDto clientLoanToDto(ClientLoan loan){
        ClientLoanDto dto = new ClientLoanDto();
        dto.setId(loan.getId());
        dto.setDues(loan.getDues());
        dto.setPendDues(loan.getPendDues());
        dto.setStatus(loan.getStatus());
        dto.setCreated_at(loan.getCreated_at());
        dto.setUpdated_at(loan.getUpdated_at());
        dto.setAccount(loan.getAccount());
        dto.setLoan(loan.getLoan());
        return dto;
    }
}
