package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.repository.SQL.SQLRegionRepositoryImpl;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.service.RegionService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLRegionServiceImpl implements RegionService {
    private final static SQLRegionServiceImpl regionService = new SQLRegionServiceImpl();
    private final SQLConnection sqlConnection = SQLConnection.getInstance();

    private SQLRegionServiceImpl() {}

    public static SQLRegionServiceImpl getInstance(){
        return regionService;
    }

    @Override
    public Region save(Region region) {
        ResultSet resultSet = null;
        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate("insert into region(region) values('" + region.getName() + "')");
            resultSet = statement.executeQuery("select ID from region order by ID desc limit 1");
            if (resultSet.next()){
                region.setId(resultSet.getLong("ID"));
            }
            resultSet.close();
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException sqlException) {
                    System.out.println(sqlException.getMessage());
                }
            }
        }
        return region;
    }

    @Override
    public Region update(Region region) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            countOfChangedRows = statement.executeUpdate("update region " +
                    "set region = '"+ region.getName() +"' " +
                    "where id = " + region.getId());
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new ServiceException("There is no region with id " + region.getId());
        return region;
    }

    @Override
    public Region getById(Long id) throws ServiceException {
        Region region = null;
        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from region where id = " + id)){
            if (resultSet.next()) {
                region = new Region(resultSet.getLong("id"), resultSet.getString("region"));
            }
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(region == null)
            throw new ServiceException("There is no region with id " + id);

        return region;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate("update user set Region_ID = null where Region_ID = " + id);
            countOfChangedRows = statement.executeUpdate("delete from region " +
                    "where id = " + id);
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new ServiceException("There is no region with id " + id);
    }

    @Override
    public List<Region> getAll() {
        ArrayList<Region> regions = new ArrayList<>();
        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from region")){
            while (resultSet.next()) {
                regions.add(new Region(resultSet.getLong("id"), resultSet.getString("region")));
            }
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        return regions;
    }
}
