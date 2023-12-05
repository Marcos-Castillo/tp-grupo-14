package com.codoacodo23650.tpgrupo14.entities.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentRequest {

    private Long loanId;
    private Double amountToPay;
    private Long accountId;

}