package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.ClientLoan;
import com.codoacodo23650.tpgrupo14.entities.dtos.ClientLoanDto;
import com.codoacodo23650.tpgrupo14.entities.dtos.TransferDto;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import com.codoacodo23650.tpgrupo14.exceptions.*;
import com.codoacodo23650.tpgrupo14.mappers.AccountMapper;
import com.codoacodo23650.tpgrupo14.mappers.ClientLoanMapper;
import com.codoacodo23650.tpgrupo14.repositories.ClientLoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanService {
    private final ClientLoanRepository repository;

    public ClientLoanService(ClientLoanRepository repository){
        this.repository = repository;
    }
    public List<ClientLoanDto> getClientLoans() {
        List<ClientLoan> client_loans = repository.findAll();
        return client_loans.stream()
                .map(ClientLoanMapper::clientLoanToDto)
                .collect(Collectors.toList());
    }

    public ClientLoanDto getClientLoanById(Long id) {
        ClientLoan entity = repository.findById(id).get();
        return ClientLoanMapper.clientLoanToDto(entity);
    }

    public String deleteClientLoan(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El prestamo del cliente con id: " + id + " ha sido eliminado";
        } else {
            return "El prestamo del cliente con id: " + id + " no ha sido eliminado";
        }

    }

    public ClientLoanDto createClientLoan(ClientLoanDto dto) {
        dto.setCreated_at(LocalDateTime.now());
        dto.setStatus(StatusLoan.PENDING);
        dto.setPendDues(dto.getDues());
        ClientLoan newClientLoan= repository.save(ClientLoanMapper.dtoToClientLoan(dto));
        return ClientLoanMapper.clientLoanToDto(newClientLoan);
    }

    public ClientLoanDto updateClientLoan(Long id, ClientLoanDto dto) {
        if (repository.existsById(id)) {
            ClientLoan clientLoanToModify = repository.findById(id).get();

            if(dto.getStatus() == StatusLoan.APPROVED || clientLoanToModify.getStatus() == StatusLoan.APPROVED) {
                if(clientLoanToModify.getStatus() == StatusLoan.REFUSED)
                {
                    throw new StatusInvalidException("Invalid finished state.");
                }

                if (dto.getPendDues() != null) {
                    if (dto.getPendDues() <= clientLoanToModify.getDues()) {
                        clientLoanToModify.setPendDues(dto.getPendDues());
                    }
                    if(dto.getPendDues() == 0)
                    {
                        clientLoanToModify.setStatus(StatusLoan.FINISHED);
                    }
                }

                if (dto.getStatus() != null) {
                    if (dto.getStatus() == StatusLoan.APPROVED) {
                        if (clientLoanToModify.getPendDues() == 0) {
                            clientLoanToModify.setStatus(StatusLoan.FINISHED);
                        } else {
                            clientLoanToModify.setStatus(dto.getStatus());
                        }
                    }
                }

                clientLoanToModify.setUpdated_at(LocalDateTime.now());

            }else{
                if(dto.getStatus() == StatusLoan.REFUSED && clientLoanToModify.getStatus() == StatusLoan.PENDING)
                {
                    clientLoanToModify.setStatus(dto.getStatus());
                }else{
                        throw new StatusInvalidException("Invalid finished state.");
                }
            }

            ClientLoan clientLoanModified = repository.save(clientLoanToModify);

            return ClientLoanMapper.clientLoanToDto(clientLoanModified);
        }
        return null;
    }
}
