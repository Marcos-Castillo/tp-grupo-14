package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import com.codoacodo23650.tpgrupo14.mappers.AccountMapper;
import com.codoacodo23650.tpgrupo14.repositories.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    private AccountRepository repository;

    public AccountService(AccountRepository repository){
        this.repository = repository;
    }
    public AccountDto getAccountById(Long id) {
        return AccountMapper.accountToDto(repository.findById(id).get());
    }


    public AccountDto createAccount(AccountDto dtoAccount) {
        Account account = AccountMapper.dtoToAccount(dtoAccount);
        for (int attempt = 1; attempt <= 5; attempt++) {
            String alias = AliasService.getAliasFromApi(account.getName());
            if (!repository.existsByAlias(alias)) {
                account.setAlias(alias);
                Account entitySaved = repository.save(account);
                return AccountMapper.accountToDto(entitySaved);
            }
        }
        throw new RuntimeException("No se pudo generar un alias único después de varios intentos");
    }
    public List<AccountDto> getAllAccount() {
        List<Account> cuentas = repository.findAll();
        return cuentas.stream()
                .map(AccountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        if(repository.existsById(id)){
            Account accounToModify = repository.findById(id).get();
            //validar que datos no vienen null parea setear en el objeto

            //logica del Patch
            if (accountDto.getAlias() != null) accounToModify.setAlias(accountDto.getAlias());
            if (accountDto.getCbu() != null) accounToModify.setCbu(accountDto.getCbu());
            if (accountDto.getName() != null) accounToModify.setName(accountDto.getName());
            if (accountDto.getAmount() != null) accounToModify.setAmount(accountDto.getAmount());
            if (accountDto.getOwner() != null) accounToModify.setOwner(accountDto.getOwner());

            Account accountModified = repository.save(accounToModify);

            return AccountMapper.accountToDto(accountModified);
        }
        return null;
    }

    public String deleteAccount(Long id) {
        repository.deleteById(id);
        return "Cuenta eliminada";
    }
}
