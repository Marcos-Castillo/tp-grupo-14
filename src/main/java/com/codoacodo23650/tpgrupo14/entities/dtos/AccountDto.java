package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.User;
import jakarta.persistence.*;
import lombok.*;

//@Builder//patron de diseño builder
@Builder
@Getter
@Setter
public class AccountDto {
    private Long id;
    private String name;
    private String cbu;
    private String alias;
    private Double amount;
}
