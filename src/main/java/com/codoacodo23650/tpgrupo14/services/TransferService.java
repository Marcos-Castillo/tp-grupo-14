package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.Transfer;
import com.codoacodo23650.tpgrupo14.entities.dtos.TransferDto;
import com.codoacodo23650.tpgrupo14.exceptions.*;
import com.codoacodo23650.tpgrupo14.mappers.TransferMapper;
import com.codoacodo23650.tpgrupo14.repositories.TransferRepository;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {
    private final TransferRepository repository;
    private final AccountRepository repo_acc;

    public TransferService(TransferRepository repository, AccountRepository repo_acc){
        this.repository = repository;
        this.repo_acc = repo_acc;
    }

    public List<TransferDto> getTransfers(){
        return repository.findAll().stream()
                .map(TransferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDto getTransferById(Long id){
        Transfer transfer = repository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transfer not found with id: " + id));
        return TransferMapper.transferToDto(transfer);
    }

    public TransferDto createTransfer(TransferDto dto){
        TransferDto resp_trans;
        resp_trans = validateTransfer(dto);

        if (!(resp_trans == null))
        {
            return resp_trans;
        }

        Account accountOrigin = repo_acc.findById(dto.getAccountOrigin()).get();
        Account accountDestination = repo_acc.findById(dto.getAccountDestination()).get();

        Double res_acc = accountOrigin.getAmount() - dto.getAmount();
        Double sum_acc = accountDestination.getAmount() + dto.getAmount();
        accountOrigin.setAmount(res_acc);
        accountDestination.setAmount(sum_acc);

        repo_acc.save(accountOrigin);
        repo_acc.save(accountDestination);

        // Setear fecha de transaccion
        dto.setDate(LocalDateTime.now());

        Transfer newTransfer = TransferMapper.dtoToTransfer(dto);
        return TransferMapper.transferToDto(repository.save(newTransfer));
    }

    public TransferDto updateTransfer(Long id, TransferDto dto) {
        if (repository.existsById(id)){
            TransferDto resp_trans;
            resp_trans = validateTransfer(dto);

            if (!(resp_trans == null))
            {
                return resp_trans;
            }

            Account accountOrigin = repo_acc.findById(dto.getAccountOrigin()).get();
            Account accountDestination = repo_acc.findById(dto.getAccountDestination()).get();

            Double res_acc = accountOrigin.getAmount() - dto.getAmount();
            Double sum_acc = accountDestination.getAmount() + dto.getAmount();
            accountOrigin.setAmount(res_acc);
            accountDestination.setAmount(sum_acc);

            repo_acc.save(accountOrigin);
            repo_acc.save(accountDestination);

            Transfer transferToModify = repository.findById(id).get();
            transferToModify.setAmount(dto.getAmount());
            transferToModify.setAccountOrigin(dto.getAccountOrigin());
            transferToModify.setAccountDestination(dto.getAccountDestination());
            Transfer transferModified = repository.save(transferToModify);

            return TransferMapper.transferToDto(transferModified);

        } else {
                throw new TransferNotFoundException("Transfer not found with id: " + id);
        }
    }

    public String deleteTransfer(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "La transferencia con id: " + id + " ha sido eliminada";
        } else {
            return "La transferencia con id: " + id + ", no ha sido eliminada";
        }
    }

    public TransferDto validateTransfer(TransferDto dto)
    {
        if(!repo_acc.existsById(dto.getAccountOrigin()))
        {
            throw new AccountNotFoundException("Account not found with id: " + dto.getAccountOrigin());
        }

        if(!repo_acc.existsById(dto.getAccountDestination()))
        {
            throw new AccountNotFoundException("Account not found with id: " + dto.getAccountDestination());
        }

        if (dto.getAccountOrigin() == dto.getAccountDestination()){
            //return null;
            throw new AccountsNotEqualsException("Origin Account is equal to Destination Account.");
        }

        if (dto.getAmount() == null || dto.getAmount() == 0){
            throw new AmountZeroException("Amount cannot be empty or zero.");
        }

        Account accountOrigin = repo_acc.findById(dto.getAccountOrigin()).get();
        Account accountDestination = repo_acc.findById(dto.getAccountDestination()).get();

        if(accountOrigin.getAmount() < dto.getAmount())
        {
            throw new InsufficientFoundsException("Insufficient funds in the account with id: " + dto.getAccountOrigin());
        }

        return null;
    }
}
