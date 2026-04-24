package com.alexhiz.store.dto;


import java.math.BigDecimal;
import java.util.UUID;

public class DetailSaleDTO {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
