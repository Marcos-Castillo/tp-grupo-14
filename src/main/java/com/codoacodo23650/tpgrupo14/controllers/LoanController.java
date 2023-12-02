package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.services.AccountService;
import com.codoacodo23650.tpgrupo14.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService service;
    @Autowired
    private AccountService accountService;

    @PostMapping("/{" +
            "}")
    public ResponseEntity<LoanDto> createLoan(@PathVariable long accountId){
        // TODO: verificar si el usuario est{a habilitado para el préstamo
        return service.createLoan(accountService.getAccountById(accountId));
    }
}
