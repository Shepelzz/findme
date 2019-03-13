package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.model.Message;

import java.util.List;

public interface MessageDAO extends GeneralDAO<Message> {
    List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError;
}
