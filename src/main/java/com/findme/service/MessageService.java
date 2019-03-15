package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Message;
import com.findme.model.MessageInfo;

import java.util.List;

public interface MessageService {

    Message save(MessageInfo messageInfo) throws InternalServerError, BadRequestException;
    Message update(Message message) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError, BadRequestException;
    Message findById(Long id) throws InternalServerError, NotFoundException;

    List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError;
}
