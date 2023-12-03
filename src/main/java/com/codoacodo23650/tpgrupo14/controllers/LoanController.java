package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.Loan;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.services.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    //  obtener todos los préstamos
    @GetMapping
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        List<LoanDto> loans = loanService.getAllLoans();
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    //  obtener todos los préstamos de un Usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LoanDto>> getAllLoansByUserId(@PathVariable Long userId) {
        List<LoanDto> loans = loanService.getAllLoansByUserId(userId);
        return new ResponseEntity<>(loans, HttpStatus.OK);
    }

    //  obtener un préstamo por ID
    @GetMapping("/{loanId}")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long loanId) {
        LoanDto loan = loanService.getLoanById(loanId);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    //  crear un nuevo préstamo
    @PostMapping()
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loan) {
        LoanDto createdLoan = loanService.createLoan(loan);
        return new ResponseEntity<>(createdLoan, HttpStatus.CREATED);
    }

    //  actualizar un préstamo existente
    @PutMapping("/{loanId}")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long loanId, @RequestBody LoanDto loanDetails) {
        LoanDto updatedLoan = loanService.updateLoan(loanId, loanDetails);
        return new ResponseEntity<>(updatedLoan, HttpStatus.OK);
    }

    //  eliminar un préstamo
    // TODO:borrado logico
    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long loanId) {
        return ResponseEntity.status(HttpStatus.OK).body(loanService.deleteLoan(loanId))
                ;
    }
}
