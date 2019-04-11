package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.model.ConversationInfo;
import com.findme.model.Message;

import java.util.List;

public interface MessageDAO extends GeneralDAO<Message> {

    void updateDateRead(String userFromId, String userToId) throws InternalServerError;
    List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError;
    List<ConversationInfo> getConversations(Long userId) throws InternalServerError;

}
