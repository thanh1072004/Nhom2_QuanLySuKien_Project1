package src.service;

import src.database.DatabaseConnection;
import src.model.Login;
import src.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.sql.*;

public class ServiceUser {
    private final Connection connection;

    public ServiceUser() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public User authorizeLogin(Login dataLogin) throws SQLException {
        User data = null;
        try(PreparedStatement ps = connection.prepareStatement("select user_id, username, password from Users where username = ? ",
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)){
            ps.setString(1, dataLogin.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.first()){
                    int userId = rs.getInt("user_id");
                    String username = rs.getString("username");
                    String storedHash = rs.getString("password");
                    if (verifyPassword(dataLogin.getPassword(), storedHash)) {
                        data = new User(userId, username, storedHash);
                    }
                }
            }
        }
        return data;
    }


    public void authorizeRegister(User user) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement("insert into Users(first_name, last_name, username, password) values(?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS)){
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getUsername());
            ps.setString(4, hashPassword(user.getPassword()));
            ps.execute();
            try(ResultSet rs = ps.getGeneratedKeys()){
                if (rs.next()) {
                int userId = rs.getInt(1);
                user.setUserId(userId);
            }}
        }
    }

    public boolean checkDuplicateUser(String username) throws SQLException {
        boolean duplicate = false;
        try(PreparedStatement p = connection.prepareStatement("select count(*) from Users where username = ?")){
            p.setString(1, username);
            try(ResultSet rs = p.executeQuery()){
                if (rs.next() && rs.getInt(1) > 0) {
                    duplicate = true;
                }
            }
        }
        return duplicate;
    }

    public User getUser(int user_id) throws SQLException {
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement("select * from Users where user_id = ?")){
            ps.setInt(1, user_id);
            try(ResultSet rs = ps.executeQuery()){if(rs.next()){
                String username = rs.getString("username");
                String password = rs.getString("password");
                user = new User(user_id, username, password);
            }

            }
        }
        return user;
    }

    public User getUser(String username) throws SQLException {
        User user = null;
        try(PreparedStatement ps = connection.prepareStatement("select * from Users where username = ?")){
            ps.setString(1, username);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    int user_id = rs.getInt("user_id");
                    String password = rs.getString("password");
                    user = new User(user_id, username, password);
                }
            }
        }
        return user;
    }

    public String getUsernameFromUserID(int userId) throws SQLException {
        String username = null;
        try(PreparedStatement ps = connection.prepareStatement("SELECT username FROM Users WHERE user_id = ?")){
            ps.setInt(1, userId);
            try(ResultSet rs = ps.executeQuery()){
                if (rs.next()) {
                    username = rs.getString("username");
                }
            }
        }
        return username;
    }

    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final int SALT_LENGTH = 16; // 16 bytes

    public static String hashPassword(String password) {
        try {
            byte[] salt = generateSalt(SALT_LENGTH);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String encodedSalt = Base64.getEncoder().encodeToString(salt);
            String encodedHash = Base64.getEncoder().encodeToString(hash);

            return encodedSalt + ":" + encodedHash;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private static byte[] generateSalt(int length) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return salt;
    }

    public static boolean verifyPassword(String password, String storedHash) {
        try {
            String[] parts = storedHash.split(":");
            byte[] salt = Base64.getDecoder().decode(parts[0]);
            byte[] hash = Base64.getDecoder().decode(parts[1]);

            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] testHash = factory.generateSecret(spec).getEncoded();

            return MessageDigest.isEqual(hash, testHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
