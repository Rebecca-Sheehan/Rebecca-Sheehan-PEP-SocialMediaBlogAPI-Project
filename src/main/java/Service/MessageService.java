package Service;

import Model.Message;
import DAO.MessageDAO;
import java.sql.SQLException;
import java.util.List;

// How do I handle exceptions in the service layer? If I throw them to the controller layer, how do I handle them there?

public class MessageService {
    public MessageDAO messageDAO;

    public MessageService() {
        this.messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    // Create a new message
    public Message createMessage(Message message) {
        try {
            String text = message.getMessage_text();
            if (text == null || text.length() == 0 || text.length() > 255)
                return null;
            if (messageDAO.checkAccountId(message.getPosted_by()) == false)
                return null;
            return messageDAO.createMessage(message);
        } catch (SQLException e) {
            return null;
        }
    }

    // Get all messages
    public List<Message> getAllMessages() {
        try {
            return messageDAO.getAllMessages();
        } catch (SQLException e) {
            return null;
        }
    }

    // Get a message by its ID
    public Message getMessageById(int message_id) {
        try {
            return messageDAO.getMessageById(message_id);
        } catch (SQLException e) {
            return null;
        }
    }

    // Delete a message by its ID
    public Message deleteMessage(int message_id) {
        try {
            return messageDAO.deleteMessage(message_id);
        } catch (SQLException e) {
            return null;
        }
    }

    // Update a message by its ID
    public Message updateMessage(int message_id, String updatedMessage) {
        try {
            if (updatedMessage == null || updatedMessage.length() == 0 || updatedMessage.length() > 255)
                return null;
            return messageDAO.updateMessage(message_id, updatedMessage);
        } catch (SQLException e) {
            return null;
        }
    }

    // Get messages by account
    public List<Message> getMessagesByAccount(int posted_by) {
        try {
            return messageDAO.getMessagesByAccount(posted_by);
        } catch (SQLException e) {
            return null;
        }
    }
}
