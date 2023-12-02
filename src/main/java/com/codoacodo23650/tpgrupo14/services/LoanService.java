package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.mappers.AccountMapper;
import com.codoacodo23650.tpgrupo14.repositories.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanService {

    private LoanRepository repository;
    public LoanService(LoanRepository repository){this.repository = repository;}

    public List<LoanDto> getLoans(){
        List<LoanDto> response = repository.findAll().stream().map(Loan)
    }

    public LoanDto createLoan(Account account){



    }

}
