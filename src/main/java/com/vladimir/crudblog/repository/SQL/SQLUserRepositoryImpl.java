package com.vladimir.crudblog.repository.SQL;

import com.vladimir.crudblog.model.User;
import com.vladimir.crudblog.service.SQLConnectionImpl;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.repository.UserRepository;
import com.vladimir.crudblog.service.mysql.service.SQLUserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class SQLUserRepositoryImpl implements UserRepository {
    private final SQLUserServiceImpl sqlUserService;

    public SQLUserRepositoryImpl() {
        try {
            this.sqlUserService = new SQLUserServiceImpl(SQLConnectionImpl.getInstance());
        } catch (SQLException sqlException) {
            throw new Error("Database connection failed +\n" + sqlException.getMessage());
        }
    }

    @Override
    public User save(User user) {
        return sqlUserService.save(user);
    }

    @Override
    public User update(User user) throws ServiceException {
        return sqlUserService.update(user);
    }

    @Override
    public User getById(Long id) throws ServiceException {
        return sqlUserService.getById(id);
    }

    @Override
    public void deleteById(Long id) throws ServiceException {
        sqlUserService.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return sqlUserService.getAll();
    }
}
