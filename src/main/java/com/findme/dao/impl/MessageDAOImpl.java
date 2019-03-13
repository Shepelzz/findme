package com.findme.dao.impl;

import com.findme.dao.MessageDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j
@Repository
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {
    private static final String SQL_MESSAGE_LIST = "SELECT msg" +
            " FROM Message msg" +
            " WHERE (msg.userFrom.id = :userFromId AND msg.userTo.id = :userToId) OR (msg.userFrom.id = :userFromId AND msg.userTo.id = :userToId)" +
            " ORDER BY msg.dateSent";

    public MessageDAOImpl() {
        setClazz(Message.class);
    }

    @Override
    public List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_MESSAGE_LIST, Message.class)
                    .setParameter("userFromId", Long.valueOf(userFromId))
                    .setParameter("userToId", Long.valueOf(userToId))
//                    .setFirstResult(rowsFrom)
//                    .setMaxResults(maxResults)
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
