package com.findme.service.impl;

import com.findme.dao.MessageDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Message;
import com.findme.model.MessageInfo;
import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.service.MessageService;
import com.findme.utils.params.MessageValidatorParams;
import com.findme.utils.validator.messageValidator.AbstractMessageValidator;
import com.findme.utils.validator.messageValidator.MessageValidator;
import com.findme.utils.validator.messageValidator.RecipientValidator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;
    private UserDAO userDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, UserDAO userDAO, RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Message save(MessageInfo messageInfo) throws InternalServerError, BadRequestException {
        validateIncomingParams(messageInfo.getUserFromId(), messageInfo.getUserToId());

        User useFrom = userDAO.findById(Long.valueOf(messageInfo.getUserFromId()));
        User userTo = userDAO.findById(Long.valueOf(messageInfo.getUserToId()));
        Relationship usersRelationship = relationshipDAO.getRelationship(messageInfo.getUserFromId(), messageInfo.getUserToId());

        Message message = new Message();
        message.setUserFrom(useFrom);
        message.setUserTo(userTo);
        message.setText(messageInfo.getText());
        message.setDateSent(new Date());

        validateMessageInfo(
            MessageValidatorParams.builder()
                .message(message)
                .relationship(usersRelationship)
                .build()
        );

        return messageDAO.save(message);
    }

    @Override
    public Message update(MessageInfo messageInfo) throws InternalServerError, BadRequestException {
        Message currentMessage = messageDAO.findById(messageInfo.getId());
        if(currentMessage.getDateRead() != null) {
            log.warn("Message "+currentMessage.getId()+" is read by recipient "+currentMessage.getUserTo().getId());
            throw new BadRequestException("Message " + currentMessage.getId() + " is read by recipient " + currentMessage.getUserTo().getId() + ". And can not be edited.");
        }

        currentMessage.setText(messageInfo.getText());
        currentMessage.setDateEdited(new Date());

        AbstractMessageValidator messageValidator = new MessageValidator();
        messageValidator.check(
            MessageValidatorParams.builder()
                .message(currentMessage)
                .build());

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
    @Transactional
    public List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError {
        messageDAO.updateDateRead(userFromId, userToId);

        return messageDAO.getMessageList(userFromId, userToId);
    }

    private void validateIncomingParams(String userFromId, String userToId) throws BadRequestException{
        try{
            Optional.of(userFromId).map(Long::valueOf);
            Optional.of(userToId).map(Long::valueOf);
        } catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    private void validateMessageInfo(MessageValidatorParams messageValidatorParams) throws BadRequestException{
        log.info("Start post validation");

        AbstractMessageValidator recipientValidator = new RecipientValidator();
        AbstractMessageValidator messageValidator = new MessageValidator();

        recipientValidator.setNextAbstractChainValidator(messageValidator);

        recipientValidator.check(messageValidatorParams);
    }
}
