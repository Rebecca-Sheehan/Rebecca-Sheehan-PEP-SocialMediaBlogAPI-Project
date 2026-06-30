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
    public Message createMessage(Message message) throws SQLException {
        messageDAO.createMessage(message);
        return message;
    }

    // Get all messages
    public List<Message> getAllMessages() throws SQLException {
        return messageDAO.getAllMessages();
    }

    // Get a message by its ID
    public Message getMessageById(int message_id) throws SQLException {
        return messageDAO.getMessageById(message_id);
    }

    // Delete a message by its ID
    public Message deleteMessage(int message_id) throws SQLException {
        return messageDAO.deleteMessage(message_id);
    }

    // Update a message by its ID
    public Message updateMessage(int message_id, String updatedMessage) throws SQLException {
        return messageDAO.updateMessage(message_id, updatedMessage);
    }

    // Get messages by account
    public List<Message> getMessagesByAccount(int posted_by) throws SQLException {
        return messageDAO.getMessagesByAccount(posted_by);
    }
}
