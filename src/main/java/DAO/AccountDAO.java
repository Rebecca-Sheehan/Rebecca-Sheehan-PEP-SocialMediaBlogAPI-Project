package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// How do I handle exceptions in the DAO layer? Do I throw them to the service layer or handle them here?

// I still need to restrict the inputs for Account.
    // The username must be greater than 0 characters
    // The password must be greater than or equal to 4 characters
    // The username cannot already exist in the Account table.

public class AccountDAO {
    
    // Method to create a new account into the database
    public Account createAccount(Account account) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Account (username, password) VALUES (?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, account.getUsername());
        ps.setString(2, account.getPassword());
        ps.executeUpdate();
        return account; // I need to fix this to return the account with the generated ID, but for now, it returns the account as is.
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
            return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
        }
        return null; // Check the 'return new Account' logic later.
    }



}