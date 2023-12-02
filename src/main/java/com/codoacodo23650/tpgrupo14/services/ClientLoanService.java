package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.ClientLoan;
import com.codoacodo23650.tpgrupo14.entities.dtos.ClientLoanDto;
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
        dto.setPendDues(dto.getDues());
        ClientLoan newAccount = repository.save(ClientLoanMapper.dtoToClientLoan(dto));
        return ClientLoanMapper.clientLoanToDto(newAccount);
    }

    public ClientLoanDto updateClientLoan(Long id, ClientLoanDto dto) {
        if (repository.existsById(id)) {
            ClientLoan clientLoanToModify = repository.findById(id).get();

            //clientLoanToModify.setAlias();
            clientLoanToModify.setUpdated_at(LocalDateTime.now());

            ClientLoan clientLoanModified = repository.save(clientLoanToModify);

            return ClientLoanMapper.clientLoanToDto(clientLoanModified);
        }
        return null;
    }
}
