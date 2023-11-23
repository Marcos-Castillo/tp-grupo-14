package com.codoacodo23650.tpgrupo14.services;


import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.Transfer;
import com.codoacodo23650.tpgrupo14.entities.dtos.TransferDto;
import com.codoacodo23650.tpgrupo14.mappers.TransferMapper;
import com.codoacodo23650.tpgrupo14.repositories.TransferRepository;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {
    private TransferRepository repository;
    private AccountRepository repo_acc;

    public TransferService(TransferRepository repository){
        this.repository = repository;
    }

    public List<TransferDto> getTransfers(){
        return repository.findAll().stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id){
        Transfer tran = repository.findById(id).get();
        return TransferMapper.transferToDto(tran);
    }

    public TransferDto createTransfer(TransferDto dto){
        //Transfer entity = TransferMapper.dtoToTransfer(dto);

        if (dto.getAccountOrigin() == null){
            return null;
        }

        if (dto.getAccountDestination() == null){
            return null;
        }

        if (dto.getAccountOrigin() == dto.getAccountDestination()){
            return null;
        }

        if (dto.getAmount() == null || dto.getAmount() == 0){
            return null;
        }

        if(!repo_acc.existsById(dto.getAccountOrigin()))
        {
            return null;
        }

        if(!repo_acc.existsById(dto.getAccountDestination()))
        {
            return null;
        }

        Account accountOrigin = repo_acc.findById(dto.getAccountOrigin()).get();

        if(accountOrigin.getAmount() < dto.getAmount())
        {
            return null;
        }

        dto.setDate(LocalDateTime.now());

        Transfer newTransfer = TransferMapper.dtoToTransfer(dto);
        return TransferMapper.transferToDto(repository.save(newTransfer));
    }

    public TransferDto updateTransfer(Long id, TransferDto dto) {
        Boolean ok_validate;

        if (repository.existsById(id)){
            Transfer transferToModify = repository.findById(id).get();

            Account accountOrigin = repo_acc.findById(dto.getAccountOrigin()).get();

            if (dto.getAmount() != null){
                ok_validate = true;

                if (dto.getAmount() == 0){
                    ok_validate = false;
                }

                if(accountOrigin.getAmount() < dto.getAmount())
                {
                    ok_validate = false;
                }

                if(ok_validate)
                {
                    transferToModify.setAmount(dto.getAmount());
                }
            }

            if (dto.getAccountOrigin() != null){
                ok_validate = true;

                if (dto.getAccountOrigin() == dto.getAccountDestination()){
                    ok_validate = false;
                }

                if(!repo_acc.existsById(dto.getAccountOrigin()))
                {
                    ok_validate = false;
                }

                if(ok_validate) {
                    transferToModify.setAccountOrigin(dto.getAccountOrigin());
                }
            }

            if (dto.getAccountDestination() != null){
                ok_validate = true;

                if (dto.getAccountOrigin() == dto.getAccountDestination()) {
                    ok_validate = false;
                }

                if(!repo_acc.existsById(dto.getAccountDestination()))
                {
                    ok_validate = false;
                }

                if(ok_validate) {
                    transferToModify.setAccountDestination(dto.getAccountDestination());
                }
            }

            Transfer transferModified = repository.save(transferToModify);

            return TransferMapper.transferToDto(transferModified);
        }

        return null;
    }

    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "La transferencia con id: " + id + " ha sido eliminada";
        } else {
            return "La transferencia con id: " + id + ", no ha sido eliminada";
        }
    }
}
