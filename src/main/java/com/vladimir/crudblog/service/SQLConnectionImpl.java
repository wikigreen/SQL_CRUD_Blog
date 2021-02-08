package com.vladimir.crudblog.service;

import java.sql.*;


public class SQLConnectionImpl implements SQLConnection {
    private static SQLConnectionImpl sqlConnectionImpl;
    private final Connection connection;
    /**
     * JDBC Driver and database url
     */
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_blog?useUnicode=true&useSSL=true&verifyServerCertificate=false";

    /**
     * User and Password
     */

    private final String USER = "blogger";
    private final String PASSWORD = "blogger";

    public static SQLConnectionImpl getInstance() throws SQLException {
        if(sqlConnectionImpl == null){
            sqlConnectionImpl = new SQLConnectionImpl();
        }
        return sqlConnectionImpl;
    }


    private SQLConnectionImpl() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    public Statement createStatement() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setEscapeProcessing(true);
        return statement;
    }
}