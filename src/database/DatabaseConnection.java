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
            String serverName = "sql12.freesqldatabase.com";
            String dbName = "sql12714426";
            String username = "sql12714426";
            String password = "LBqcSrnSdP";
            String url = String.format("jdbc:mysql://%s:%d/%s", serverName, 3306, dbName);
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Connection established");
            } catch (ClassNotFoundException | SQLException e) {

                e.printStackTrace();
                throw new SQLException("Failed to connect to the database", e);
            }
        }
    }
}
