package com.findme.service.impl;

import com.findme.dao.MessageDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Message;
import com.findme.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    private MessageDAO messageDAO;

    @Autowired
    public MessageServiceImpl(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    @Override
    public Message save(Message message) throws InternalServerError, BadRequestException {
        return messageDAO.save(message);
    }

    @Override
    public Message update(Message message) throws InternalServerError, BadRequestException {
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
}
