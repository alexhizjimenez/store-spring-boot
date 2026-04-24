package com.alexhiz.store.service.impl;

import com.alexhiz.store.model.Sale;
import com.alexhiz.store.repository.IGenericRepo;
import com.alexhiz.store.repository.ISaleRepo;
import com.alexhiz.store.service.ISaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaleImpl extends CRUDImpl<Sale, UUID> implements ISaleService{
    private final ISaleRepo repo;
    @Override
    protected IGenericRepo<Sale, UUID> getRepo() {
        return repo;
    }
}
