package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.service.PostService;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.service.ServiceException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLPostServiceImpl implements PostService {
    private SQLConnection sqlConnection;

    public SQLPostServiceImpl(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    @Override
    public Post save(Post post) {
        ResultSet resultSet = null;

        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate("insert into post(content) " +
                    "values ('" + post.getContent() + "')");
            resultSet = statement.executeQuery("select ID from post order by ID desc limit 1");
            if (resultSet.next()){
                post.setId(resultSet.getLong("ID"));
            }
            resultSet.close();

        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException sqlException) {
                    System.out.println(sqlException.getMessage());
                }
            }
        }
        return post;
    }

    @Override
    public Post update(Post post) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            countOfChangedRows = statement.executeUpdate("update post "
                    + "set content = '" + post.getContent() + "', "
                    + "update_date = current_time()"
                    + "where id = " + post.getId());
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new ServiceException("There is no post with id " + post.getId());
        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from post where id = " + post.getId())){
            if (resultSet.next()) {
                post = new Post(resultSet.getLong("ID"),
                        resultSet.getString("content"),
                        new java.util.Date(resultSet.getTimestamp("create_date").getTime()),
                        new java.util.Date(resultSet.getTimestamp("update_date").getTime()));
            }
        } catch (SQLException e){
            throw new Error(e.getMessage());
        }
        return post;
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        Post post = null;
        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from post where id = " + id)){
            if (resultSet.next()) {
                post = new Post(resultSet.getLong("ID"),
                        resultSet.getString("content"),
                        new java.util.Date(resultSet.getTimestamp("create_date").getTime()),
                        new java.util.Date(resultSet.getTimestamp("update_date").getTime()));
            }
        } catch (SQLException e){
            throw new Error(e.getMessage());
        }
        if(post == null)
            throw new ServiceException("There is no post with id " + id);
        return post;
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        int countOfChangedRows;
        try (Statement statement = sqlConnection.createStatement()){
            statement.executeUpdate("delete from users_posts where Post_ID = " + id);
            countOfChangedRows = statement.executeUpdate(
                    "delete from post " +
                            "where id = " + id);
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new ServiceException("There is no post with id " + id);
    }

    @Override
    public List<Post> getAll() {
        ArrayList<Post> posts = new ArrayList<>();
        try (Statement statement = sqlConnection.createStatement();
             ResultSet resultSet = statement.executeQuery("select * from post")){
            while (resultSet.next()) {
                posts.add(new Post(resultSet.getLong("ID"),
                        resultSet.getString("content"),
                        new java.util.Date(resultSet.getTimestamp("create_date").getTime()),
                        new java.util.Date(resultSet.getTimestamp("update_date").getTime())));
            }
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        return posts;
    }
}
