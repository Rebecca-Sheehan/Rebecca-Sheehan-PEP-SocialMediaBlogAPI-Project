package DAO;

import Util.ConnectionUtil;
import Model.Message;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    
    // Method to create a new message 
    public Message createMessage(Message message) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3, message.getTime_posted_epoch());
        ps.executeUpdate();
        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int generatedId = rs.getInt(1);
            message.setMessage_id(generatedId);
        }
        return message;
    }

    // Method to get all messages
    public List<Message> getAllMessages() throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        return messages;
    }

    // Method to get a message by its ID
    public Message getMessageById(int message_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM Message WHERE message_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            return message;
        }
        return null;
    }

    // Method to delete a message by its ID
    public Message deleteMessage(int message_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        Message messageToDelete = getMessageById(message_id);
        String sql = "DELETE FROM Message WHERE message_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);
        ps.executeUpdate();
        return messageToDelete;
    }

    // Method to update a message by its ID
    public Message updateMessage(int message_id, String updatedtext) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, updatedtext);
        ps.setInt(2, message_id);
        ps.executeUpdate();
        return getMessageById(message_id);
    }

    // Method to get messages by account
    public List<Message> getMessagesByAccount(int posted_by) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message WHERE posted_by = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, posted_by);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Message message = new Message(
                    rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"),
                    rs.getLong("time_posted_epoch"));
            messages.add(message);
        }
        return messages;
    }

    // Method to check if an account exists
    public boolean checkAccountId(int account_id) throws SQLException {
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT COUNT(account_id) FROM Account WHERE account_id = ?;";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, account_id);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        if (count == 1)
            return true;
        return false;
    }
}
