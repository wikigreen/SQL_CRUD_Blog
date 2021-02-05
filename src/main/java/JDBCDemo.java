import java.sql.*;

public class JDBCDemo {
    /**
     * JDBC Driver and database url
     */
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/crud_blog?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false";

    /**
     * User and Password
     */
    static final String USER = "root";
    static final String PASSWORD = "password";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Statement statement = null;

        System.out.println("Registering JDBC driver...");

        Class.forName("com.mysql.cj.jdbc.Driver");

        System.out.println("Creating database connection...");
        connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

        System.out.println("Executing statement...");
        statement = connection.createStatement();


        String sql;
        sql = "select * from user";

        PreparedStatement preparedStatement = connection.prepareStatement("");

        ResultSet resultSet = preparedStatement.executeQuery(sql);

        while (resultSet.next()) {
            String name = resultSet.getString("role");
            System.out.println("Name: " + name);
        }


        System.out.println("Closing connection and releasing resources...");
        resultSet.close();
        statement.close();
        connection.close();
    }
}
