package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Transfer;
import com.codoacodo23650.tpgrupo14.entities.dtos.TransferDto;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class TransferMapper {
    public Transfer dtoToTransfer(TransferDto dto){
        Transfer transfer = new Transfer();
        transfer.setAmount(dto.getAmount());
        transfer.setAccountOrigin(dto.getAccountOrigin());
        transfer.setAccountDestination(dto.getAccountDestination());
        transfer.setDate(LocalDateTime.now());
        return transfer;
    }

    public TransferDto transferToDto(Transfer transfer){
        TransferDto dto = new TransferDto();
        dto.setAmount(transfer.getAmount());
        dto.setAccountOrigin(transfer.getAccountOrigin());
        dto.setAccountDestination(transfer.getAccountDestination());
        dto.setDate(transfer.getDate());
        return dto;
    }
}
