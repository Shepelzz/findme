package com.findme.dao.impl;

import com.findme.dao.MessageDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Message;
import com.findme.model.ConversationInfo;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.ArrayList;
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
    private static final String SQL_GET_CONVERSATIONS = "" +
            "SELECT  DATA.userId," +
            "    DATA.user_firstName," +
            "    DATA.user_lastName," +
            "    m.ID msg_id," +
            "    m.TEXT msg_text," +
            "    m.USER_FROM_ID msg_userFromId," +
            "    m.USER_TO_ID msg_userToId," +
            "    m.DATE_SENT msg_dateSent," +
            "    m.DATE_READ msg_dateRead," +
            "    DATA.cnt_msgId" +
            "    FROM (" +
            "            SELECT" +
            "            u.id userId," +
            "            u.LAST_NAME  user_lastName," +
            "            u.FIRST_NAME user_firstName," +
            "            MAX(msg.Id) max_msgId," +
            "            COUNT(CASE WHEN msg.DATE_READ IS NULL THEN msg.id END) cnt_msgId" +
            "    FROM RELATIONSHIP r" +
            "    JOIN USERS u" +
            "    ON ((r.USER_FROM_ID = :userId AND r.USER_TO_ID = u.id) OR (r.USER_TO_ID = :userId AND r.USER_FROM_ID = u.id))" +
            "    LEFT JOIN MESSAGE msg ON ((msg.USER_FROM_ID = u.id AND msg.USER_TO_ID = :userId) OR" +
            "                              (msg.USER_FROM_ID = :userId AND msg.USER_TO_ID = u.id))" +
            "    AND msg.DATE_DELETED IS NULL" +
            "    WHERE r.STATUS = 'FRIENDS'" +
            "    GROUP BY u.id," +
            "    u.LAST_NAME," +
            "    u.FIRST_NAME" +
            ") DATA\n" +
            "    LEFT JOIN MESSAGE m ON DATA.max_msgId = m.ID" +
            "    ORDER BY m.DATE_SENT DESC, DATA.user_lastName ASC";


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

    public List<ConversationInfo> getConversations(Long userId) throws InternalServerError {
        List<ConversationInfo> resultList = new ArrayList<>();
        try {
            List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(SQL_GET_CONVERSATIONS)
                    .setParameter("userId", userId)
                    .getResultList();

            for (Object[] obj : list) {
                resultList.add(ConversationInfo.builder()
                        .userId(((BigInteger) obj[0]).longValue())
                        .userFirstName((String) obj[1])
                        .userLastName((String) obj[2])
                        .messageId(obj[3] != null ?((BigInteger) obj[3]).longValue():null)
                        .messageText(obj[4] != null ?(String) obj[4]:null)
                        .messageUserFromId(obj[5] != null ?((BigInteger) obj[5]).longValue():null)
                        .messageUserToId(obj[6] != null ?((BigInteger) obj[6]).longValue():null)
                        .messageDateSent(obj[7] != null ?(Date) obj[7]:null)
                        .messageDateRead(obj[8] != null ?(Date) obj[8]:null)
                        .newMessagesCount((obj[9] != null && (Integer) obj[9] > 0) ?(Integer) obj[9]:null)
                        .build());
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
        return resultList;
    }
}
