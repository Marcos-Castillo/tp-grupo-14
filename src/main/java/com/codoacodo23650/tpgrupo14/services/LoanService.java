package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.entities.enums.StatusLoan;
import com.codoacodo23650.tpgrupo14.exceptions.AccountNotFoundException;
import com.codoacodo23650.tpgrupo14.exceptions.LoanDueException;
import com.codoacodo23650.tpgrupo14.exceptions.StatusInvalidException;
import com.codoacodo23650.tpgrupo14.mappers.LoanMapper;
import com.codoacodo23650.tpgrupo14.mappers.TransferMapper;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import com.codoacodo23650.tpgrupo14.repositories.LoanRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanService {

    private final LoanRepository repository;
    private final AccountRepository accountRepository;

    public LoanService(LoanRepository loanRepository,AccountRepository accountRepository) {

        this.repository = loanRepository;
        this.accountRepository = accountRepository;
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
        Account account = accountRepository.findById(loan.getAccount().getId()).orElseThrow(() -> new AccountNotFoundException("Cuenta inexistente."));
        Loan loanToSave = LoanMapper.dtoToLoan(loan);
        loanToSave.setCreated_at(LocalDateTime.now());
        loanToSave.setUpdated_at(LocalDateTime.now());
        loanToSave.setDuesAmount((loan.getAmount() * (1 + (loan.getInterest() / 100))) / loan.getDues());
        loanToSave.setStatus(StatusLoan.PENDING);
        account.setAmount(account.getAmount()+loan.getAmount());
        accountRepository.save(account);
        return LoanMapper.loanToDto(repository.save(loanToSave));
    }


    public LoanDto updateLoan(Long loanId, LoanDto loanDetails) {
        Loan existingLoan = repository.findById(loanId).get();

        if (loanDetails.getAmount() != null) existingLoan.setAmount(loanDetails.getAmount());
        if (loanDetails.getInterest() != null) existingLoan.setInterest(loanDetails.getInterest());
        if (loanDetails.getDues() != null) existingLoan.setDues(loanDetails.getDues());
        if (loanDetails.getDate() != null) existingLoan.setDate(loanDetails.getDate());
        if (loanDetails.getStatus() != null) existingLoan.setStatus(loanDetails.getStatus());
        existingLoan.setUpdated_at(LocalDateTime.now());
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
    @Transactional
    public String payment(Long loanId, Double amountToPay, Long accountId) {
        // controla existencia de Loan
        if (repository.existsById(loanId)){
            // controla existencia de Account
            if (accountRepository.existsById(accountId))
            {
                Loan existingLoan = repository.findById(loanId).get();
                // controla estado del Loan
                if (existingLoan.getStatus() == StatusLoan.FINISHED || existingLoan.getStatus() == StatusLoan.REFUSED) {
                    throw new StatusInvalidException("Prestamo con estado FINISHED o REFUSED");
                } else {
                    Account existingAccount = accountRepository.findById(accountId).get();
                    // controla que el pago sea > 0 y que haya saldo en Account
                    if (amountToPay > 0 && amountToPay <= existingAccount.getAmount()) {
                        // controla que el pago sea > o = al valor de la cuota
                        if (amountToPay >= existingLoan.getDuesAmount()) {
                            // actualiza saldo a pagar del Loan
                            existingLoan.setAmount(existingLoan.getAmount() - (amountToPay / (1 + (existingLoan.getInterest() / 100))));
                            // actualiza cuotas restantes del Loan
                            existingLoan.setDues(existingLoan.getDues() - 1);
                            // actualiza estado el Loan si corresponde
                            if (existingLoan.getDues() == 0 || !(existingLoan.getAmount() > 0)) {
                                existingLoan.setStatus(StatusLoan.FINISHED);
                            }
                            // actualiza saldo en Account
                            existingAccount.setAmount(existingAccount.getAmount() - amountToPay);
                            // si el pago es distinto al valor de las cuotas, recalcula el importe
                            // de las mismas dividieno el saldo del Loan por las cuots pendientes
                            if (!amountToPay.equals(existingLoan.getDuesAmount())){
                                existingLoan.setDuesAmount((existingLoan.getAmount() * (1 + (existingLoan.getInterest() / 100))) / existingLoan.getDues());
                            }
                            // guarda cuenta
                            accountRepository.save(existingAccount);
                            // actualiza fecha de update y guarda Loan
                            existingLoan.setUpdated_at(LocalDateTime.now());
                            repository.save(existingLoan);
                            return "El préstamo con id: " + loanId + " ha sido abonado " + amountToPay + " desde cuenta " + accountId;
                        } else {
                            throw new LoanDueException("El valor minimo a abonar debe ser mayor o igual al valor de cuota ($" + existingLoan.getDuesAmount() + ").");
                        }
                    }
                }
            }
            return "La cuenta con id: " + accountId + ", no existe o tiene saldo insuficiente.";

        } else {
            return "El préstamo con id: " + loanId + ", no existe .";
        }
    }
}
