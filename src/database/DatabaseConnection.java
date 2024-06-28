package src.database;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseConnection {
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

    private static final class InstanceHolder {
        private static final DatabaseConnection instance = new DatabaseConnection();
    }

    public static DatabaseConnection getInstance() {
        return InstanceHolder.instance;
    }

    public Connection getConnection() {
        return connection;
    }

    private void loadProperties() throws IOException {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("src/resources/config.properties")) {
            if (input == null) {
                throw new FileNotFoundException("config.properties file not found in the classpath");
            }
            properties.load(input);
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
                throw new SQLException("Failed to connect to the database", e);
            }
        }
    }
}
