package com.alexhiz.store.dto;

import com.alexhiz.store.model.SaleStatus;
import jakarta.persistence.Column;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class SaleDTO {
    private UUID id;
    private LocalDate date;
    private UUID branchId;
    private SaleStatus status;
    private List<DetailSaleDTO> detail;
    private BigDecimal total;
}
