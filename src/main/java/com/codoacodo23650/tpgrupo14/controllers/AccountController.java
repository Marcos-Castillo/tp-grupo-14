package com.codoacodo23650.tpgrupo14.controllers;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import com.codoacodo23650.tpgrupo14.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    private AccountService service;

    // Obtener una lista de cuentas registrados
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccount(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllAccount());
    }

    // Obtener la info de un solo cuenta
    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAccountById(id));
    }

    // Crear/Registrar un cuenta

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto account){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createAccount(account));
    }

    // Modificar un cuenta (PUT)
    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDto> updateFullAccount(@PathVariable Long id, @RequestBody AccountDto accountDto)
    {
        return ResponseEntity.status(HttpStatus.OK).body(service.updateAccount(id, accountDto));
    }

    // Eliminar un cuenta
    //TODO:borrado logico
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteAccount(id));
    }

}
