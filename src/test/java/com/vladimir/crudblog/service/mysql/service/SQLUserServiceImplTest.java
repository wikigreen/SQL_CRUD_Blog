package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.model.Role;
import com.vladimir.crudblog.model.User;
import com.vladimir.crudblog.repository.PostRepository;
import com.vladimir.crudblog.repository.RegionRepository;
import com.vladimir.crudblog.repository.SQL.SQLPostRepositoryImpl;
import com.vladimir.crudblog.repository.SQL.SQLRegionRepositoryImpl;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.ServiceException;
import com.vladimir.crudblog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLUserServiceImplTest {
    @Mock private SQLConnection sqlConnectionMock;
    @Mock private Statement statementMock;
    @Mock private ResultSet resultSetMock;
    @Mock private RegionRepository regionRepositoryMock;
    @Mock private PostRepository postRepositoryMock;
    private UserService sqlUserService;

    @BeforeEach
    public void setup() throws SQLException {
        this.sqlConnectionMock = mock(SQLConnection.class);
        this.statementMock = mock(Statement.class);
        this.resultSetMock = mock(ResultSet.class);
        this.regionRepositoryMock = mock(SQLRegionRepositoryImpl.class);
        this.postRepositoryMock = mock(SQLPostRepositoryImpl.class);
        this.sqlUserService = new SQLUserServiceImpl(sqlConnectionMock);
        when(sqlConnectionMock.createStatement()).thenReturn(statementMock);
    }

    @Test
    public void testIdGeneration() throws SQLException{
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(statementMock.executeUpdate(anyString())).thenReturn(1);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("ID")).thenReturn(1L);

        User user = sqlUserService.save(new User(null, "FirstName", "LastName",
                                        new ArrayList<>(), new Region(1L, "region"), Role.ADMIN));
        assertNotNull(user.getId());
    }

    @Test
    public void testUpdateUserNotFound() throws SQLException{
        when(statementMock.executeUpdate(anyString())).thenReturn(0);

        assertThrows(ServiceException.class,
                () -> sqlUserService.update(new User(null, "FirstName", "LastName",
                new ArrayList<>(), new Region(1L, "region"), Role.ADMIN)));
    }

    @Test
    public void testGetByIDRegionNotFound() throws SQLException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertThrows(ServiceException.class, () -> sqlUserService.getById(1L));
    }

    @Test
    public void testGetById() throws SQLException, ServiceException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("id")).thenReturn(1L);
        when(resultSetMock.getString("region")).thenReturn("Test");

        User expectedUser = new User();
        User actualUser = sqlUserService.getById(1L);

        assertEquals(expectedUser, actualUser);
    }



}
