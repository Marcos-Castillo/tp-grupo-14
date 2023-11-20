package com.codoacodo23650.tpgrupo14.services;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    public Account getAccountById(Long id) {

        return Account.builder().build();

    }

    public AccountDto createAccount(AccountDto account) {
        return AccountDto.builder().build();
    }

    public List<AccountDto> getAllAccount() {

        return List.of(AccountDto.builder().build());
    }

    public AccountDto updateAccount(Long id, AccountDto accountDto) {
        return AccountDto.builder().build();
    }

    public String deleteAccount(Long id) {
        return "borrado";
    }
}
