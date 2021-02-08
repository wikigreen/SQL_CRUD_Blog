package com.vladimir.crudblog.controller;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.repository.RegionRepository;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.repository.SQL.SQLRegionRepositoryImpl;

import java.util.List;

public class RegionController {
    private final RegionRepository repository;

    public RegionController() {
        this.repository = new SQLRegionRepositoryImpl();
    }

    public List<Region> getAll(){
        return repository.getAll();
    }

    public Region addRegion(String name){
        Region region = new Region(null, name);
        repository.save(region);
        return region;
    }

    public Region getByID(Long id) throws ServiceException {
        return repository.getById(id);
    }


    public void deleteByID(Long id) throws ServiceException {
        repository.deleteById(id);
    }

    public Region update(Region region) throws ServiceException {
        repository.update(region);
        return region;
    }

}
