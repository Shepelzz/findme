package com.findme.service.impl;

import com.findme.dao.MessageDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Message;
import com.findme.model.MessageInfo;
import com.findme.model.User;
import com.findme.service.MessageService;
import com.findme.utils.params.MessageValidatorParams;
import com.findme.utils.validator.messageValidator.AbstractMessageValidator;
import com.findme.utils.validator.messageValidator.MessageValidator;
import com.findme.utils.validator.messageValidator.RecipientValidator;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;
    private UserDAO userDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO, UserDAO userDAO) {
        this.messageDAO = messageDAO;
        this.userDAO = userDAO;
    }

    @Override
    public Message save(MessageInfo messageInfo) throws InternalServerError, BadRequestException {
        validateIncomingParams(messageInfo.getUserFromId(), messageInfo.getUserToId());

        User useFrom = userDAO.findById(Long.valueOf(messageInfo.getUserFromId()));
        User userTo = userDAO.findById(Long.valueOf(messageInfo.getUserToId()));

        Message message = new Message();
        message.setUserFrom(useFrom);
        message.setUserTo(userTo);
        message.setText(messageInfo.getText());
        message.setDateSent(new Date());

        return messageDAO.save(message);
    }

    @Override
    public Message update(MessageInfo messageInfo) throws InternalServerError, BadRequestException {
        Message message = new Message();
        return messageDAO.update(message);
    }

    @Override
    public void delete(Long id) throws InternalServerError, BadRequestException {
        messageDAO.delete(id);
    }

    @Override
    public Message findById(Long id) throws InternalServerError, NotFoundException {
        return messageDAO.findById(id);
    }

    @Override
    public List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError {
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

    private void validatePostInfo(MessageValidatorParams messageValidatorParams) throws BadRequestException{
        log.info("Start post validation");

        AbstractMessageValidator recipientValidator = new RecipientValidator();
        AbstractMessageValidator messageValidator = new MessageValidator();

        recipientValidator.setNextAbstractChainValidator(messageValidator);

        recipientValidator.check(messageValidatorParams);
    }
}
