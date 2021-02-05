package com.vladimir.crudblog.repository.SQL;

import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.repository.PostRepository;
import com.vladimir.crudblog.repository.RepositoryException;
import com.vladimir.crudblog.service.SQLService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SQLPostRepositoryImpl implements PostRepository {
    private final static SQLPostRepositoryImpl postRepository = new SQLPostRepositoryImpl();
    private final SQLService sqlService = SQLService.getInstance();

    private SQLPostRepositoryImpl() {}

    public static SQLPostRepositoryImpl getInstance() {
        return postRepository;
    }

    @Override
    public Post save(Post post) {
        ResultSet resultSet = null;

        try (Statement statement = sqlService.createStatement()){
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
    public Post update(Post post) throws RepositoryException {
        int countOfChangedRows;
        try (Statement statement = sqlService.createStatement()){
            countOfChangedRows = statement.executeUpdate("update post "
                    + "set content = '" + post.getContent() + "', "
                    + "update_date = current_time()"
                    + "where id = " + post.getId());
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new RepositoryException("There is no post with id " + post.getId());
        return post;
    }

    @Override
    public Post getById(Long id) throws RepositoryException {
        Post post = null;
        try (Statement statement = sqlService.createStatement();
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
            throw new RepositoryException("There is no post with id " + id);
        return post;
    }

    @Override
    public void deleteById(Long id) throws RepositoryException {
        int countOfChangedRows;
        try (Statement statement = sqlService.createStatement()){
            statement.executeUpdate("delete from users_posts where Post_ID = " + id);
            countOfChangedRows = statement.executeUpdate(
                "delete from post " +
                    "where id = " + id);
        } catch (SQLException sqlE) {
            throw new Error(sqlE.getMessage());
        }
        if(countOfChangedRows < 1)
            throw new RepositoryException("There is no post with id " + id);
    }

    @Override
    public List<Post> getAll() {
        ArrayList<Post> posts = new ArrayList<>();
        try (Statement statement = sqlService.createStatement();
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
