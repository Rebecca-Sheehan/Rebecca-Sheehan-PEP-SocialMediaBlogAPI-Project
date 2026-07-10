package Controller;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

public class SocialMediaController {

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::createAccountHandler);
        app.post("/login", this::verifyAccountHandler);
        app.post("/messages", this::createMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageHandler);
        app.patch("/messages/{message_id}", this::updateMessageHandler);
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);
        //app.start(8080); // The tests threw a java.lang.IllegalStateException because the server was started twice
        return app;
    } 

    // Handler to create a new account
    private void createAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account createdAccount = accountService.createAccount(account);
        if (createdAccount != null) {
            context.json(mapper.writeValueAsString(createdAccount)).status(200);
        } else {
            context.status(400);
        }
    }

    // Handler to verify Account and login credentials
    private void verifyAccountHandler(Context context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account verifiedAccount = accountService.verifyAccount(account);
        if (verifiedAccount != null) {
            context.json(mapper.writeValueAsString(verifiedAccount)).status(200);
        } else {
            context.status(401);
        }
    }

    // Handler to create a new message
    private void createMessageHandler(Context context)  {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Message message = mapper.readValue(context.body(), Message.class);
            Message createdMessage = messageService.createMessage(message);
            if (createdMessage != null)
                context.json(createdMessage).status(200);
            else
                context.status(400);
        } catch (JsonProcessingException e) {
                context.status(400);
        }
    }

    // Handler to get all messages
    private void getAllMessagesHandler(Context context) {
        List<Message> messages = messageService.getAllMessages();
        context.json(messages).status(200);
    }

    // Handler to get a message by its ID
    private void getMessageByIdHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.getMessageById(message_id);
        if (message != null) 
            context.json(message).status(200);
        if (message == null)
            context.status(200);
    }

    // Handler to delete a message by its ID
    private void deleteMessageHandler(Context context) {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMessage = messageService.deleteMessage(message_id);
        if (deletedMessage != null) 
            context.json(deletedMessage).status(200);
        if (deletedMessage == null)
            context.status(200);
    }

    // Handler to update a message by its ID
    private void updateMessageHandler(Context context) throws JsonProcessingException {
        int message_id = Integer.parseInt(context.pathParam("message_id"));
        ObjectMapper mapper = new ObjectMapper();
        Message updatedMessage = mapper.readValue(context.body(), Message.class);
        Message result = messageService.updateMessage(message_id, updatedMessage.getMessage_text());
        if (result != null) {
            context.json(result).status(200);
        } else {
            context.status(400);
        }
    }

    // Handler to get messages by account
    private void getMessagesByAccountHandler(Context context) {
        int posted_by = Integer.parseInt(context.pathParam("account_id"));
        List<Message> messages = messageService.getMessagesByAccount(posted_by);
        context.json(messages).status(200);
    }
}