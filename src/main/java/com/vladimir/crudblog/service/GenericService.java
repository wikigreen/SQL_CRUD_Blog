package com.vladimir.crudblog.service;

import com.vladimir.crudblog.repository.RepositoryException;

import java.util.List;

public interface GenericService<T>  {
    T save(T t);

    T update(T t) throws RepositoryException;

    T getById(Long id) throws RepositoryException;

    void deleteById(Long id) throws RepositoryException;

    List<T> getAll();
}
