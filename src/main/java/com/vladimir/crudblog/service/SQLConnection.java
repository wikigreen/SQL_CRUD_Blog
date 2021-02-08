package com.vladimir.crudblog.service;

import java.sql.SQLException;
import java.sql.Statement;

public interface SQLConnection {
    Statement createStatement() throws SQLException;
}
