package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TransferDto {

    private Long id;

    private Double amount;

    private Account accountOrigin;

    private Account accountDestination;

    private LocalDateTime date;

}