package com.findme.test;

import com.findme.exception.InternalServerError;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j
@Repository
public class DAO {
    @PersistenceContext
    private EntityManager entityManager;

    private static final String SQL_GET_CONVERSATIONS = "" +
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
                    .build());
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
        return resultList;
    }
}
