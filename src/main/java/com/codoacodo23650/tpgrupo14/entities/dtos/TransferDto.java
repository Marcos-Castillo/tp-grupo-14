package com.codoacodo23650.tpgrupo14.entities.dtos;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferDto {

    private Long id;
    private Double amount;
    private Account accountOrigin;
    private Account accountDestination;
    private LocalDateTime date;

}
