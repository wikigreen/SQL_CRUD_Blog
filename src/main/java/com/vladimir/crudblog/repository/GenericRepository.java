package com.vladimir.crudblog.repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T save(T t);

    T update(T t) throws RepositoryException;

    T getById(ID id) throws RepositoryException;

    void deleteById(ID id) throws RepositoryException;

    List<T> getAll();
}
