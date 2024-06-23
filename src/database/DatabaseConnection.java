package src.database;

import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private Properties properties;

    private DatabaseConnection() {
        try {
            loadProperties();
            connectToDatabase();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Failed to connect to the database", e);
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

    private void loadProperties() throws IOException {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream("src/resources/config.properties")) {
            properties.load(fis);
        }
    }

    public void connectToDatabase() throws SQLException {
        if (connection == null) {
            String serverName = properties.getProperty("db.serverName");
            String dbName = properties.getProperty("db.dbName");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

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
