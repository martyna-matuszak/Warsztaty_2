package pl.coderslab.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/workshops_2?useSSL=false&characterEncoding=utf8";
    private static final String USER = "root";
    private static final String PASSWORD = "coderslab";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
