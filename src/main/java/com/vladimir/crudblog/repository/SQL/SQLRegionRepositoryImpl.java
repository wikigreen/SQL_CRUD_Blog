package com.vladimir.crudblog.repository.SQL;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.repository.RegionRepository;
import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.service.mysql.service.SQLRegionServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class SQLRegionRepositoryImpl implements RegionRepository {
    private final SQLRegionServiceImpl sqlRegionService;

    public SQLRegionRepositoryImpl() {
        try {
            this.sqlRegionService = new SQLRegionServiceImpl(SQLConnectionImpl.getInstance());
        } catch (SQLException sqlException) {
            throw new Error("Database connection failed +\n" + sqlException.getMessage());
        }
    }

    @Override
    public Region save(Region region) {
        return sqlRegionService.save(region);
    }

    @Override
    public Region update(Region region) throws ServiceException {
        return sqlRegionService.update(region);
    }

    @Override
    public Region getById(Long id) throws ServiceException {
        return sqlRegionService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        sqlRegionService.deleteById(id);
    }

    @Override
    public List<Region> getAll() {
        return sqlRegionService.getAll();
    }
}
