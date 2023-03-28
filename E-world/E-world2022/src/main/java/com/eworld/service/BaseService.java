package com.eworld.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseService <E> {
    public E findById(Integer id);

    public E create (E entity);

    public E getDetails(Integer id);

    public Page<E> findPaging(E entity, Pageable pageable);

    public E update(Integer id , E entity);

    public E findByName(String name);
}
