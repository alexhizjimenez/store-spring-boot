package com.alexhiz.store.service;

import com.alexhiz.store.model.Sale;

import java.util.List;
import java.util.UUID;

import com.alexhiz.store.dto.SaleDTO;

public interface ISaleService extends ICRUD<Sale, UUID> {

    List<SaleDTO> getAllSales() throws Exception;

    SaleDTO createSale(SaleDTO saleDto) throws Exception;

    SaleDTO updateSale(UUID id, SaleDTO saleDto) throws Exception;

    void deleteSale(UUID id) throws Exception;

}
