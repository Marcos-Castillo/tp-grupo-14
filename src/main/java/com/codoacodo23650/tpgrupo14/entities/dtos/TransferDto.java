package com.codoacodo23650.tpgrupo14.entities.dtos;

import com.codoacodo23650.tpgrupo14.entities.Account;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Double amount;
    private Long accountOrigin;
    private Long accountDestination;
    private LocalDateTime date;

}
