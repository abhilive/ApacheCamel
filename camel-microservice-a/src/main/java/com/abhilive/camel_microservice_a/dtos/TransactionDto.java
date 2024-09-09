package com.abhilive.camel_microservice_a.dtos;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TransactionDto implements Serializable {

    private Long transactionId;
    private Long senderAccountId;
    private Long receiverAccountId;
    private Double amount;
    private String currency;
    private Date transactionDate;

}
