package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {
    @Autowired
    private LoanService service;

    // Obtener una lista de prestamos registrados

    @GetMapping
    public ResponseEntity<List<LoanDto>> getLoans(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getLoans());
    }

    // Obtener la info de un solo prestamo

    @GetMapping(value = "/{id}")
    public ResponseEntity<LoanDto> getLoanById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getLoanById(id));
    }

    // Crear/Registrar un prestamo

    @PostMapping
    public ResponseEntity<LoanDto> createLoan(@RequestBody LoanDto loan){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createLoan(loan));
    }

    // Modificar un prestamo
    @PutMapping(value = "/{id}")
    public ResponseEntity<LoanDto> updateLoan(@PathVariable Long id, @RequestBody LoanDto loanDto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateLoan(id, loanDto));
    }

    // Eliminar un prestamo

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteLoan(id));
    }

}
