package com.codoacodo23650.tpgrupo14.mappers;

import com.codoacodo23650.tpgrupo14.entities.Account;
import com.codoacodo23650.tpgrupo14.entities.dtos.AccountDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountMapper {
    public AccountDto accountToDto(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .type(account.getType())
                //.name(account.getName())
                .cbu(account.getCbu())
                .alias(account.getAlias())
                .amount(account.getAmount())
                .owner(account.getOwner())
                .build();
    }

    public Account dtoToAccount(AccountDto dto) {
        return new Account(
                (dto.getId()),
                (dto.getType()),
                //(dto.getName()),
                (dto.getCbu()),
                (dto.getAlias()),
                (dto.getAmount()),
                (dto.getOwner())
                );
    }
}
