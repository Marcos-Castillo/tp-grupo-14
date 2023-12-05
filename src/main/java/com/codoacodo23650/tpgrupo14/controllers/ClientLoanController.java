package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.dtos.ClientLoanDto;
import com.codoacodo23650.tpgrupo14.entities.dtos.LoanDto;
import com.codoacodo23650.tpgrupo14.services.ClientLoanService;
import com.codoacodo23650.tpgrupo14.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client_loans")
public class ClientLoanController {
    @Autowired
    private ClientLoanService service;

    // Obtener una lista de prestamos de clientes

    @GetMapping
    public ResponseEntity<List<ClientLoanDto>> getClientLoans(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getClientLoans());
    }

    // Obtener la info de un solo prestamo de cliente

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientLoanDto> getClientLoanById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getClientLoanById(id));
    }

    // Crear/Registrar detalle prestamo de cliente

    @PostMapping
    public ResponseEntity<ClientLoanDto> createClientLoan(@RequestBody ClientLoanDto clientLoan){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createClientLoan(clientLoan));
    }

    // Modificar un prestamo de un cliente
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientLoanDto> updateClientLoan(@PathVariable Long id, @RequestBody ClientLoanDto clientLoanDto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateClientLoan(id, clientLoanDto));
    }

    // Eliminar un prestamo de un cliente

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteClientLoan(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteClientLoan(id));
    }

    // Cancelar una cuota
    @PutMapping(value = "/pay/{id}")
    public ResponseEntity<ClientLoanDto> payDue(@PathVariable Long id, @RequestBody ClientLoanDto dto){
        return ResponseEntity.status(HttpStatus.OK).body(service.payClientLoanDue(id, dto));
    }

}
