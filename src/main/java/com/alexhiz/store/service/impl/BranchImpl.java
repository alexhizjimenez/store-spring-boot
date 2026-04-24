package com.alexhiz.store.service.impl;

import com.alexhiz.store.model.Branch;
import com.alexhiz.store.repository.IBranchRepo;
import com.alexhiz.store.repository.IGenericRepo;
import com.alexhiz.store.service.IBranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
@RequiredArgsConstructor
public class BranchImpl extends CRUDImpl<Branch, UUID> implements IBranchService {
    private final IBranchRepo repo;
    @Override
    protected IGenericRepo<Branch, UUID> getRepo() {
        return repo;
    }
}
