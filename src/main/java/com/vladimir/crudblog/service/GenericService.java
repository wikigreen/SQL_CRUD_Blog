package com.vladimir.crudblog.service;

import java.util.List;

public interface GenericService<T>  {
    T save(T t);

    T update(T t) throws ServiceException;

    T getById(Long id) throws ServiceException;

    void deleteById(Long id) throws ServiceException;

    List<T> getAll();
}
