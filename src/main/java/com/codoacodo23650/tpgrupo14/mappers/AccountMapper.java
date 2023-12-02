package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import com.codoacodo23650.tpgrupo14.services.UserService;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;

@UtilityClass
public class AccountMapper {
    public AccountDto accountToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .cbu(account.getCbu())
                .alias(account.getAlias())
                .amount(account.getAmount())
//                .owner(account.getOwner().getId())
                .build();
    }

    public Account dtoToAccount(AccountDto dto) {
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .cbu(dto.getCbu())
                .alias(dto.getAlias())
                .amount(dto.getAmount())
//                .owner(userService.getUserById(dto.getOwner()))
                .build();
    }
}
