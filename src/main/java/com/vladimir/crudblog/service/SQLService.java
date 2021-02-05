package com.vladimir.crudblog.service;

import java.sql.*;


public class SQLService {
    private static SQLService sqlService;
    private final Connection connection;
    private static boolean isInitialized = false;

    /**
     * JDBC Driver and database url
     */
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_blog?useUnicode=true&useSSL=true&verifyServerCertificate=false";

    /**
     * User and Password
     */
    private final String USER = "blogger";
    private final String PASSWORD = "blogger";

    public static SQLService initialize() throws SQLException {
        if(!isInitialized){
            sqlService = new SQLService();
            isInitialized = true;
            return sqlService;
        }
        throw new IllegalStateException("SQLService has already been initialized");
    }

    public static SQLService getInstance(){
        if(isInitialized)
            return sqlService;
        throw new IllegalStateException("SQLService has not been initialized");
    }

    private SQLService () throws SQLException {
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