package com.alexhiz.store.service.impl;

import com.alexhiz.store.exception.ModelNotFoundException;
import com.alexhiz.store.repository.IGenericRepo;
import com.alexhiz.store.service.ICRUD;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public abstract  class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T save(T entity) throws Exception {
        return getRepo().save(entity);
    }

    @Override
    public T update(ID id, T entity) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("No se encontro"));
        return getRepo().save(entity);
    }

    @Override
    public List<T> findAll() throws Exception {
        return getRepo().findAll();
    }

    @Override
    public T findById(ID id) throws Exception {
        return getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("No se encontro"));
    }

    @Override
    public void delete(ID id) throws Exception {
        getRepo().findById(id).orElseThrow(()-> new ModelNotFoundException("No se encontro"));
        getRepo().deleteById(id);
    }

    @Override
    public Page<T> listPage(Pageable pageable) throws Exception {
        return getRepo().findAll(pageable);
    }
}
