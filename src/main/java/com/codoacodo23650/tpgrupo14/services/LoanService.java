package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import com.codoacodo23650.tpgrupo14.mappers.LoanMapper;
import com.codoacodo23650.tpgrupo14.mappers.TransferMapper;
import com.codoacodo23650.tpgrupo14.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository repository;

    public LoanService(LoanRepository loanRepository) {
        this.repository = loanRepository;
    }

    public List<LoanDto> getAllLoans() {
        return repository.findAll().stream()
                .map(LoanMapper::loanToDto)
                .collect(Collectors.toList());
    }
    public List<LoanDto> getAllLoansByUserId(Long userId) {
        return repository.findLoansByUserId(userId).stream()
                .map(LoanMapper::loanToDto)
                .collect(Collectors.toList());
            }

    public LoanDto getLoanById(Long loanId) {
        LoanDto loanDto = LoanMapper.loanToDto(repository.findById(loanId).get());
        return loanDto;
    }

    public LoanDto createLoan(LoanDto loan) {
        Loan loanToSave = LoanMapper.dtoToLoan(loan);
        loanToSave.setStatus(StatusLoan.PENDING);
        return LoanMapper.loanToDto(repository.save(loanToSave));
    }


    public LoanDto updateLoan(Long loanId, LoanDto loanDetails) {
        Loan existingLoan = repository.findById(loanId).get();

        if (loanDetails.getAmount() != null) existingLoan.setAmount(loanDetails.getAmount());
        if (loanDetails.getInterest() != null) existingLoan.setInterest(loanDetails.getInterest());
        if (loanDetails.getDues() != null) existingLoan.setDues(loanDetails.getDues());
        if (loanDetails.getDate() != null) existingLoan.setDate(loanDetails.getDate());
        if (loanDetails.getStatus() != null) existingLoan.setStatus(loanDetails.getStatus());

        return LoanMapper.loanToDto( repository.save(existingLoan));
    }

    public String deleteLoan(Long loanId) {
        if (repository.existsById(loanId)){
            repository.deleteById(loanId);
            return "El préstamo con id: " + loanId + " ha sido eliminada";
        } else {
            return "El préstamo con id: " + loanId + ", no ha sido eliminada";
        }


    }
}
