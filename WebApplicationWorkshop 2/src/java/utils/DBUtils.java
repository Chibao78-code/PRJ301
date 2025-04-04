package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
private static final String DB_URL = "jdbc:sqlserver://localhost:1433;databaseName=OnlineExamDB";
private static final String DB_USERNAME = "sa";
private static final String DB_PASSWORD = "12345";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}