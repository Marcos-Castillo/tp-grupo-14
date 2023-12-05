package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.ClientLoan;
import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.ClientLoanDto;
import com.codoacodo23650.tpgrupo14.mappers.AccountMapper;
import com.codoacodo23650.tpgrupo14.mappers.ClientLoanMapper;
import com.codoacodo23650.tpgrupo14.mappers.LoanMapper;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import com.codoacodo23650.tpgrupo14.repositories.ClientLoanRepository;
import com.codoacodo23650.tpgrupo14.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLoanService {
    private final ClientLoanRepository repository;
    private final LoanService loanService;
    private final AccountService accountService;

    public ClientLoanService(ClientLoanRepository repository, LoanService loanService, AccountService accountService){
        this.repository = repository;
        this.loanService = loanService;
        this.accountService = accountService;
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

        ClientLoan clientLoan = ClientLoanMapper.dtoToClientLoan(dto);
        Loan loan = LoanMapper.dtoToLoan(loanService.getLoanById(dto.getLoan().getId()));
        Account account = AccountMapper.dtoToAccount(accountService.getAccountById(dto.getAccount().getId()));
        account.setAmount(account.getAmount() + loan.getAmount());
        clientLoan.setAccount(account);
        clientLoan.setLoan(loan);
        clientLoan.setCreated_at(LocalDateTime.now());
        clientLoan.setPendDues(clientLoan.getDues());
        clientLoan.setDueAmmount((loan.getAmount()*(1+(loan.getInterest())))/clientLoan.getDues());

        ClientLoan newClientLoan = repository.save(ClientLoanMapper.dtoToClientLoan(ClientLoanMapper.clientLoanToDto(clientLoan)));
        accountService.updateAccount(account.getId(), AccountMapper.accountToDto(account));
        //newClientLoan.getAccount().setAmount(dto.getAccount().getAmount() + dto.getLoan().getAmount());
        return getClientLoanById(newClientLoan.getId());
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

    public ClientLoanDto payClientLoanDue(Long id, ClientLoanDto dto){
        if(repository.existsById(id)) {
            ClientLoan clientLoan = ClientLoanMapper.dtoToClientLoan(getClientLoanById(id));
            clientLoan.payDue();
            return updateClientLoan(id, ClientLoanMapper.clientLoanToDto(clientLoan));
        } else {
            // TODO: Throw exeption
            return null;
        }
    }
}
