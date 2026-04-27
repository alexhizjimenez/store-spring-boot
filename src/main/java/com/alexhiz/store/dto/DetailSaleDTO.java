package com.alexhiz.store.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailSaleDTO {
    private UUID id;
    private UUID productId;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
