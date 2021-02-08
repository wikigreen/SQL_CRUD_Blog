package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.model.Role;
import com.vladimir.crudblog.model.User;
import com.vladimir.crudblog.repository.SQL.SQLPostRepositoryImpl;
import com.vladimir.crudblog.repository.SQL.SQLRegionRepositoryImpl;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.service.UserService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLUserServiceImpl implements UserService {
    private SQLConnection sqlConnection;

    public SQLUserServiceImpl(SQLConnection sqlConnection) {
       this.sqlConnection = sqlConnection;
    }

    @Override
    public User save(User user) {
        ResultSet resultSet = null;
        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate(String.format(
                    "insert into user(First_name, Last_name, Region_ID, Role) " +
                            "values('%s', '%s', %d, '%s')",

                    user.getFirstName(),
                    user.getLastName(),
                    (user.getRegion() == null ? null : user.getRegion().getId()),
                    user.getRole().toString()));

            resultSet = statement.executeQuery("select ID from user order by ID desc limit 1");
            if(resultSet.next()){
                user.setId(resultSet.getLong("ID"));
            }

            user.getPosts().forEach(post -> {
                try {
                    statement.executeUpdate(String.format(
                            "insert into users_posts(Person_ID, Post_ID) " +
                                    "values (%d, %d)",

                            user.getId(),
                            post.getId()));
                } catch (SQLException sqlE) {
                    throw new Error(sqlE.getMessage());
                }
            });
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException sqlE) {
                    System.out.println(sqlE.getMessage());
                }
            }
        }
        return user;
    }

    @Override
    public User update(User user) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            countOfChangedRows = statement.executeUpdate(String.format(
                    "update user " +
                            "set First_name = '%s', " +
                            "Last_name = '%s', " +
                            "Region_ID = %d, " +
                            "Role = '%s' " +
                            "where id = %d ",

                    user.getFirstName(),
                    user.getLastName(),
                    (user.getRegion() == null ? null : user.getRegion().getId()),
                    user.getRole().toString(),
                    user.getId()
            ));

            if(countOfChangedRows < 1) {
                throw new ServiceException("There is no user with id " + user.getId());
            }

            statement.executeUpdate("delete from users_posts where Person_ID = " + user.getId());
            user.getPosts().forEach(post -> {
                try {
                    statement.executeUpdate(String.format(
                            "insert into users_posts(Person_ID, Post_ID) " +
                                    "values (%d, %d)",

                            user.getId(),
                            post.getId()));
                } catch (SQLException sqlE) {
                    throw new Error(sqlE.getMessage());
                }
            });
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }

        return user;
    }

    @Override
    public User getById(Long id) throws ServiceException {
        User user = null;
        try(Statement statement = sqlConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user where id = " + id)){
            if (resultSet.next()) {
                Region region = null;
                try {
                    region = new SQLRegionRepositoryImpl().getById(resultSet.getLong("Region_ID"));
                }catch (ServiceException e){
                    System.out.println(e.getMessage());
                }
                user = new User(id, resultSet.getString("First_name"),
                        resultSet.getString("Last_name"),
                        new ArrayList<>(),
                        region,
                        Role.parseRole(resultSet.getString("Role")));
            }
        } catch (SQLException sqlE){
            throw new Error(sqlE.getMessage());
        }
        if(user == null)
            throw new ServiceException("There is no user with id " + id);

        try (Statement statement = sqlConnection.createStatement();
             ResultSet usersPostsSet = statement.executeQuery("select Post_ID from users_posts where Person_ID = " + id)){
            while (usersPostsSet.next()) {
                try {
                    user.getPosts().add(new SQLPostRepositoryImpl().getById(usersPostsSet.getLong("Post_ID")));
                } catch (ServiceException ignored){}
            }
        } catch (SQLException sqlE){
            throw new Error(sqlE.getMessage());
        }
        return user;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate("delete from users_posts where Person_ID = " + id);
            countOfChangedRows = statement.executeUpdate(
                    "delete from user " +
                            "where ID = " + id);
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new ServiceException("There is no user with id " + id);
    }

    @Override
    public List<User> getAll() {
        ArrayList<User> users = new ArrayList<>();

        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from user")){
            while (resultSet.next()) {
                Region region = null;
                try {
                    region = new SQLRegionRepositoryImpl().getById(resultSet.getLong("Region_ID"));
                } catch (ServiceException ignored) {
                }
                User user = new User(resultSet.getLong("ID"), resultSet.getString("First_name"),
                        resultSet.getString("Last_name"),
                        new ArrayList<>(),
                        region,
                        Role.parseRole(resultSet.getString("Role")));
                users.add(user);
            }
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }


        users.forEach(user -> {
            try(Statement statement = sqlConnection.createStatement();
                ResultSet usersPostsSet = statement.executeQuery("select Post_ID from users_posts where Person_ID = " + user.getId())){
                while (usersPostsSet.next()) {
                    try {
                        user.getPosts().add(new SQLPostRepositoryImpl().getById(usersPostsSet.getLong("Post_ID")));
                    } catch (ServiceException ignored){}
                }
            } catch (SQLException sqlE) {
                throw new Error(sqlE.getMessage());
            }
        });
        return users;
    }
}
