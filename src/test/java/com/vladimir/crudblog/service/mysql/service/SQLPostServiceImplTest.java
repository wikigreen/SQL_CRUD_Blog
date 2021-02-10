package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Post;
import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SQLPostServiceImplTest {
    @Mock private SQLConnection sqlConnectionMock;
    @Mock private Statement statementMock;
    @Mock private ResultSet resultSetMock;
    private SQLPostServiceImpl sqlPostService;

    @BeforeEach
    public void setup() throws SQLException {
        this.sqlConnectionMock = mock(SQLConnection.class);
        this.statementMock = mock(Statement.class);
        this.resultSetMock = mock(ResultSet.class);
        this.sqlPostService = new SQLPostServiceImpl(sqlConnectionMock);
        when(sqlConnectionMock.createStatement()).thenReturn(statementMock);
    }

    @Test
    public void testIdGeneration() throws SQLException{
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(statementMock.executeUpdate(anyString())).thenReturn(1);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("ID")).thenReturn(1L);

        Post post = sqlPostService.save(new Post(null, "Post Test"));
        assertNotNull(post.getId());
    }

    @Test
    public void testUpdatePostNotFound() throws SQLException{
        when(statementMock.executeUpdate(anyString())).thenReturn(0);

        assertThrows(ServiceException.class, () -> sqlPostService.update(new Post(1L, "Test")));
    }

    @Test
    public void testGetByIDWherePostNotFound() throws SQLException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertThrows(ServiceException.class, () -> sqlPostService.getById(1L));
    }

    @Test
    public void testGetById() throws SQLException, ServiceException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("ID")).thenReturn(1L);
        when(resultSetMock.getString("content")).thenReturn("Test");
        long createTime = new Date().getTime();
        when(resultSetMock.getTimestamp("create_date")).thenReturn(new Timestamp(createTime));
        long updTime = new Date().getTime() + 100L;
        when(resultSetMock.getTimestamp("update_date")).thenReturn(new Timestamp(updTime));

        Post rightPost = new Post(1L, "Test", new Date(createTime), new Date(updTime));
        Post post = sqlPostService.getById(1L);
        assertEquals(rightPost, post);
    }

    @Test
    public void testDeleteByIdWherePostNotFound() throws SQLException {
        when(statementMock.executeUpdate(anyString())).thenReturn(0);

        assertThrows(ServiceException.class, () -> sqlPostService.deleteById(1L));
    }

    @Test
    public void testGetAllWhereNoData() throws SQLException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertEquals(sqlPostService.getAll().size(), 0);
    }

    @Test
    public void testGetAll() throws SQLException {
        List<Post> posts = Arrays.asList(
                new Post(1L, "Test1", new Date(12345678L), new Date(234567889L)),
                new Post(2L, "Test2", new Date(12345678L), new Date(234567889L)),
                new Post(3L, "Test3", new Date(12345678L), new Date(234567889L)),
                new Post(4L, "Test4", new Date(12345678L), new Date(234567889L)),
                new Post(5L, "Test5", new Date(12345678L), new Date(234567889L)));

        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true, true, true, true, true, false);
        when(resultSetMock.getLong("ID")).thenReturn(1L, 2L, 3L, 4L, 5L);
        when(resultSetMock.getString("content")).thenReturn("Test1", "Test2", "Test3", "Test4", "Test5");
        when(resultSetMock.getTimestamp("create_date")).thenReturn(new Timestamp(12345678L));
        when(resultSetMock.getTimestamp("update_date")).thenReturn(new Timestamp(234567889L));

        assertEquals(posts, sqlPostService.getAll());
    }

}
