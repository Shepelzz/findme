package com.findme.dao.impl;

import com.findme.dao.MessageDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Message;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Log4j
@Repository
public class MessageDAOImpl extends GeneralDAOImpl<Message> implements MessageDAO {
    private static final String SQL_MESSAGE_LIST = "SELECT msg" +
            " FROM Message msg" +
            " WHERE ((msg.userFrom.id = :userFromId AND msg.userTo.id = :userToId) OR (msg.userFrom.id = :userToId AND msg.userTo.id = :userFromId))" +
            " AND msg.dateDeleted IS NULL" +
            " ORDER BY msg.dateSent";
    private static final String SQL_UPDATE_MESSAGE_DATE_READ = "UPDATE MESSAGE" +
            " SET DATE_READ = :dateRead" +
            " WHERE USER_FROM_ID = :userToId AND USER_TO_ID = :userFromId" +
            " AND DATE_READ IS NULL";

    public MessageDAOImpl() {
        setClazz(Message.class);
    }

    @Override
    public void updateDateRead(String userFromId, String userToId) throws InternalServerError {
        log.info("");
        try {
            int res = entityManager.createNativeQuery(SQL_UPDATE_MESSAGE_DATE_READ)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .setParameter("dateRead", new Date())
                    .executeUpdate();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Message> getMessageList(String userFromId, String userToId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_MESSAGE_LIST, Message.class)
                    .setParameter("userFromId", Long.valueOf(userFromId))
                    .setParameter("userToId", Long.valueOf(userToId))
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
