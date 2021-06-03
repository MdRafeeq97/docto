package com.app.docto.models.request;

import lombok.Data;

@Data
public class PaymentRequest {
    private Double amount;
    private String currency;
    private String token;
}
