package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDAO {
    
    // Method to create a new account into the database
    public Account createAccount(Account account) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int generatedId = rs.getInt(1);
            account.setAccount_id(generatedId);
        }
        return account; 
    }

    // Method to verify if an account exists in the database
    public Account verifyAccount(Account account) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Account WHERE username = ? AND password = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return new Account(
                rs.getInt("account_id"), 
                rs.getString("username"), 
                rs.getString("password"));
        }
        return null;
    }

    // Method to check if a username is available
    public boolean checkUsername(Account account) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT COUNT(username) FROM Account WHERE username = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, account.getUsername());
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 0)
            return true;
        return false;
    }
}