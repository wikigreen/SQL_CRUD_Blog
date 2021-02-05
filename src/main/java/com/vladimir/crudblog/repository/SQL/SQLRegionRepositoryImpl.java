package com.vladimir.crudblog.repository.SQL;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.repository.RegionRepository;
import com.vladimir.crudblog.repository.RepositoryException;
import com.vladimir.crudblog.service.SQLService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLRegionRepositoryImpl implements RegionRepository {
    private final static SQLRegionRepositoryImpl regionRepository = new SQLRegionRepositoryImpl();
    private final SQLService sqlService = SQLService.getInstance();

    private SQLRegionRepositoryImpl() {}

    public static SQLRegionRepositoryImpl getInstance(){
        return regionRepository;
    }

    @Override
    public Region save(Region region) {
        ResultSet resultSet = null;
        try (Statement statement = sqlService.createStatement()){
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
    public Region update(Region region) throws RepositoryException {
        int countOfChangedRows;
        try (Statement statement = sqlService.createStatement()){
            countOfChangedRows = statement.executeUpdate("update region " +
                                        "set region = '"+ region.getName() +"' " +
                                        "where id = " + region.getId());
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new RepositoryException("There is no region with id " + region.getId());
        return region;
    }

    @Override
    public Region getById(Long id) throws RepositoryException {
        Region region = null;
        try (Statement statement = sqlService.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from region where id = " + id)){
            if (resultSet.next()) {
                region = new Region(resultSet.getLong("id"), resultSet.getString("region"));
            }
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(region == null)
            throw new RepositoryException("There is no region with id " + id);

        return region;
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        int countOfChangedRows;
        try (Statement statement = sqlService.createStatement()){
            statement.executeUpdate("update user set Region_ID = null where Region_ID = " + id);
            countOfChangedRows = statement.executeUpdate("delete from region " +
                    "where id = " + id);
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new RepositoryException("There is no region with id " + id);

    }

    @Override
    public List<Region> getAll() {
        ArrayList<Region> regions = new ArrayList<>();
        try (Statement statement = sqlService.createStatement();
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
