package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.mappers.LoanMapper;
import com.codoacodo23650.tpgrupo14.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private LoanRepository repository;
    public LoanService(LoanRepository repository){

        this.repository = repository;
    }

    public List<LoanDto> getLoans(){
        List<Loan> loans = repository.findAll();
        List<LoanDto> loansDtos = loans.stream()
                .map(LoanMapper::loanToDto)
                .collect(Collectors.toList());
        return loansDtos;
    }

    public LoanDto getLoanById(Long id){
        Loan loan = repository.findById(id).get();
        return LoanMapper.loanToDto(loan);
    }

    public LoanDto createLoan(LoanDto loan){
        Loan entity = LoanMapper.dtoToLoan(loan);
        entity.setCreated_at(LocalDateTime.now());
        Loan entitySaved = repository.save(entity);
        loan = LoanMapper.loanToDto(entitySaved);
        return loan;
    }

    public String deleteLoan(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El préstamo con id: " + id + " ha sido eliminado";
        } else {
            return "El préstamo con id: " + id + ", no ha sido eliminado";
        }
    }

    public LoanDto updateLoan(Long id, LoanDto dto){
        if(repository.existsById(id)){
            Loan loanToModify = repository.findById(id).get();

            loanToModify.setAmount(dto.getAmount());
            loanToModify.setInterest(dto.getInterest());
            loanToModify.setUpdated_at(LocalDateTime.now());

            Loan loanModified = repository.save(loanToModify);
            return LoanMapper.loanToDto(loanModified);
        }
        return null;
    }

}
