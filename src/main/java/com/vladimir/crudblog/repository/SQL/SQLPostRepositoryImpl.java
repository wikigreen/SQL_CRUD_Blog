package com.vladimir.crudblog.repository.SQL;

import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.repository.PostRepository;
import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.service.mysql.service.SQLPostServiceImpl;
import com.vladimir.crudblog.service.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class SQLPostRepositoryImpl implements PostRepository {
    private final SQLPostServiceImpl sqlPostService;

    public SQLPostRepositoryImpl() {
        try {
            this.sqlPostService = new SQLPostServiceImpl(SQLConnectionImpl.getInstance());
        } catch (SQLException sqlException) {
            throw new Error("Database connection failed +\n" + sqlException.getMessage());
        }
    }

    @Override
    public Post save(Post post) {
        return sqlPostService.save(post);
    }

    @Override
    public Post update(Post post) throws ServiceException {
        return sqlPostService.update(post);
    }

    @Override
    public Post getById(Long id) throws ServiceException {
        return sqlPostService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        sqlPostService.deleteById(id);
    }

    @Override
    public List<Post> getAll() {
        return sqlPostService.getAll();
    }
}
