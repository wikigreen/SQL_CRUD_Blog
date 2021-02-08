package com.vladimir.crudblog.service.mysql.service;

import com.vladimir.crudblog.model.Region;
import com.vladimir.crudblog.service.SQLConnection;
import com.vladimir.crudblog.service.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class SQLRegionServiceImplTest {
    @Mock private SQLConnection sqlConnectionMock;
    @Mock private Statement statementMock;
    @Mock private ResultSet resultSetMock;
    private SQLRegionServiceImpl sqlRegionService;

    @BeforeEach
    public void setup() throws SQLException{
        this.sqlConnectionMock = mock(SQLConnection.class);
        this.statementMock = mock(Statement.class);
        this.resultSetMock = mock(ResultSet.class);
        this.sqlRegionService = new SQLRegionServiceImpl(sqlConnectionMock);
        when(sqlConnectionMock.createStatement()).thenReturn(statementMock);
    }

    @Test
    public void testIdGeneration() throws SQLException{
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(statementMock.executeUpdate(anyString())).thenReturn(1);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("ID")).thenReturn(1L);

        Region region = sqlRegionService.save(new Region(null, "Region,"));
        assertNotNull(region.getId());
    }

    @Test
    public void testResultSetClosing() throws SQLException{
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(statementMock.executeUpdate(anyString())).thenReturn(1);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("ID")).thenReturn(1L);

        sqlRegionService.save(new Region(null, "Region,"));
        verify(resultSetMock).close();
    }

    @Test
    public void testRegionNotFoundUpdate() throws SQLException{
        when(statementMock.executeUpdate(anyString())).thenReturn(0);

        assertThrows(ServiceException.class, () -> sqlRegionService.update(new Region(null, "Test")));
    }

    @Test
    public void testGetByIDWhereRegionNotFound() throws SQLException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertThrows(ServiceException.class, () -> sqlRegionService.getById(1L));
    }

    @Test
    public void testGetById() throws SQLException, ServiceException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(true);
        when(resultSetMock.getLong("id")).thenReturn(1L);
        when(resultSetMock.getString("region")).thenReturn("Test");

        assertEquals(new Region(1L, "Test"), sqlRegionService.getById(1L));
    }

    @Test
    public void testDeleteByIdWhereRegionNotFound() throws SQLException {
        when(statementMock.executeUpdate(anyString())).thenReturn(0);

        assertThrows(ServiceException.class, () -> sqlRegionService.deleteById(1L));
    }

    @Test
    public void testGetAllWhereNoData() throws SQLException {
        when(statementMock.executeQuery(anyString())).thenReturn(resultSetMock);
        when(resultSetMock.next()).thenReturn(false);

        assertEquals(sqlRegionService.getAll().size(), 0);
    }
}
