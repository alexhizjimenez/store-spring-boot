package com.alexhiz.store.service.impl;

import com.alexhiz.store.model.Product;
import com.alexhiz.store.repository.IGenericRepo;
import com.alexhiz.store.repository.IProductRepo;
import com.alexhiz.store.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProductImpl extends CRUDImpl<Product, UUID> implements IProductService {
    private final IProductRepo repo;
    @Override
    protected IGenericRepo<Product, UUID> getRepo() {
        return repo;
    }
}
