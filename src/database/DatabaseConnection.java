package src.database;

import java.sql.*;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            connectToDatabase();
        } catch (SQLException e) {

            throw new RuntimeException("Failed to connect to the database");
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void connectToDatabase() throws SQLException {
        if (connection == null) {
        	String serverName = "LAPTOP-MAULSVT1\\SQLEXPRESS";
            String dbName = "Event Management";
            String username = "sa";
            String password = "123456";
            String url = "jdbc:sqlserver://" + serverName + ":1433;DatabaseName=" + dbName + ";encrypt=true;trustServerCertificate=true";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection established");
            } catch (ClassNotFoundException | SQLException e) {

                throw new SQLException("Failed to connect to the database", e);
            }
        }
    }
}
