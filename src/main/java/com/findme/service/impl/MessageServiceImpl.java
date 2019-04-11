package com.findme.service.impl;

import com.findme.dao.MessageDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Message;
import com.findme.model.Relationship;
import com.findme.service.MessageService;
import com.findme.types.RelationshipStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Log4j
@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
        this.messageDAO = messageDAO;
    }

    @Override
    public Message save(Message message) throws InternalServerError, BadRequestException {
        validateMessageInfo(message);
        message.setDateSent(new Date());
        return messageDAO.save(message);
    }

    @Override
    public Message update(Message message) throws InternalServerError, BadRequestException {
        Message currentMessage = messageDAO.findById(message.getId());
        if(currentMessage.getDateRead() != null) {
            log.warn("Message "+currentMessage.getId()+" is read by recipient "+currentMessage.getUserTo().getId());
            throw new BadRequestException("Message " + currentMessage.getId() + " is read by recipient " + currentMessage.getUserTo().getId() + ". And can not be edited.");
        }
//        validateMessageInfo(message);

        currentMessage.setText(message.getText());
        currentMessage.setDateEdited(message.getDateEdited());
        currentMessage.setDateRead(message.getDateRead());
        return messageDAO.update(currentMessage);
    }

    @Override
    public void delete(Long id) throws InternalServerError, BadRequestException {
        Message currentMessage = messageDAO.findById(id);
        if(currentMessage.getDateRead() != null) {
            log.warn("Message "+id+" is read by recipient "+currentMessage.getUserTo().getId());
            throw new BadRequestException("Message "+id+" is read by recipient "+currentMessage.getUserTo().getId()+". And can not be deleted.");
        }
        currentMessage.setDateDeleted(new Date());
        messageDAO.update(currentMessage);
    }

    @Override
    public Message findById(Long id) throws InternalServerError, NotFoundException {
        return messageDAO.findById(id);
    }

    @Override
    public List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError {
//        messageDAO.updateDateRead(userFromId, userToId);
        return messageDAO.getMessageList(userFromId, userToId);
    }

    private void validateMessageInfo(Message message) throws BadRequestException{
        log.info("Message text ["+message.getText()+"] validation");
        if(message.getText().length() > 140) {
            log.warn("Message text validation fail. Message ["+message.getText()+"] is more than 140 symbols. userId: "+message.getUserTo().getId());
            throw new BadRequestException("Message text validation fail. Message ["+message.getText()+"] is more than 140 symbols. userId: "+message.getUserTo().getId());
        }

        log.info("Message recipient relationship status from user "+message.getUserFrom().getId()+" to user "+message.getUserTo().getId()+" validation");
        Relationship usersRelationship = relationshipDAO.getRelationship(String.valueOf(message.getUserFrom().getId()), String.valueOf(message.getUserTo().getId()));
        if(usersRelationship == null || usersRelationship.getStatus() != RelationshipStatus.FRIENDS) {
            log.warn("Message recipient validation fail. Recipient is not a friend. userId: " + message.getUserTo().getId());
            throw new BadRequestException("Message recipient validation fail. Recipient is not a friend. userId: " + message.getUserTo().getId());
        }
    }
}
