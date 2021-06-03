package com.app.docto.models.response;

import com.app.docto.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private String transactionId;
    private TransactionStatus transactionStatus;
    private Double amount;
    private String currency;
}
