package com.findme.test;

import com.findme.exception.InternalServerError;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Map;

@Log4j
@Repository
public class DAO {
    @PersistenceContext
    private EntityManager entityManager;



    public List<ConversationInfo> getConversations(Long userId) throws InternalServerError {



        String SQL_GET_RELATIONSHIP = "" +
                "SELECT  DATA.userId,\n" +
                "    DATA.user_firstName,\n" +
                "    DATA.user_lastName,\n" +
                "    m.ID msg_id,\n" +
                "    m.TEXT msg_text,\n" +
                "    m.USER_FROM_ID msg_userFromId,\n" +
                "    m.USER_TO_ID msg_userToId,\n" +
                "    m.DATE_SENT msg_dateSent,\n" +
                "    m.DATE_READ msg_dateRead\n" +
                "    FROM (\n" +
                "            SELECT\n" +
                "                    u.id         userId,\n" +
                "            u.LAST_NAME  user_lastName,\n" +
                "            u.FIRST_NAME user_firstName,\n" +
                "            MAX(msg.Id) max_msgId\n" +
                "    FROM RELATIONSHIP r\n" +
                "    JOIN USERS u\n" +
                "    ON ((r.USER_FROM_ID = :userId AND r.USER_TO_ID = u.id) OR (r.USER_TO_ID = :userId AND r.USER_FROM_ID = u.id))\n" +
                "    LEFT JOIN MESSAGE msg ON ((msg.USER_FROM_ID = u.id AND msg.USER_TO_ID = :userId) OR\n" +
                "                              (msg.USER_FROM_ID = :userId AND msg.USER_TO_ID = u.id))\n" +
                "    AND msg.DATE_DELETED IS NULL\n" +
                "    WHERE r.STATUS = 'FRIENDS'\n" +
                "    GROUP BY u.id,\n" +
                "    u.LAST_NAME,\n" +
                "    u.FIRST_NAME\n" +
                ") DATA\n" +
                "    LEFT JOIN MESSAGE m ON DATA.max_msgId = m.ID\n" +
                "    ORDER BY m.DATE_SENT DESC, DATA.user_lastName ASC";

        try {
            Query query = entityManager.createNativeQuery(SQL_GET_RELATIONSHIP);
            query.setParameter("userId", userId);
            List<Map> list = query.getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
        return null;
    }






}
