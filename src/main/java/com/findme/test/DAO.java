package com.findme.test;

import com.findme.exception.InternalServerError;
import com.findme.model.Relationship;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Log4j
@Repository
public class DAO {
    @PersistenceContext
    private EntityManager entityManager;



    public List<ConversationInfo> getConversations(Long userId) throws InternalServerError {



        String SQL_GET_RELATIONSHIP = "" +
                "SELECT  new com.findme.test.ConversationInfo(2," +
                "        'dfdf'," +
                "        'eeee'," +
                "        4," +
                "        'dfdfd'," +
                "        1," +
                "        11," +
                "        '2019-01-01'," +
                "        '2019-02-02')" +
                " FROM User u";

        try {
            TypedQuery<ConversationInfo> query = entityManager.createQuery(SQL_GET_RELATIONSHIP, ConversationInfo.class);


            return query.getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }


//    SELECT  DATA.userId,
//    DATA.user_firstName,
//    DATA.user_lastName,
//    m.ID msg_id,
//    m.TEXT msg_text,
//    m.USER_FROM_ID msg_userFromId,
//    m.USER_TO_ID msg_userToId,
//    m.DATE_SENT msg_dateSent,
//    m.DATE_READ msg_dateRead
//    FROM (
//            SELECT
//                    u.id         userId,
//            u.LAST_NAME  user_lastName,
//            u.FIRST_NAME user_firstName,
//            MAX(msg.Id) max_msgId
//    FROM RELATIONSHIP r
//    JOIN USERS u
//    ON ((r.USER_FROM_ID = :userId AND r.USER_TO_ID = u.id) OR (r.USER_TO_ID = :userId AND r.USER_FROM_ID = u.id))
//    LEFT JOIN MESSAGE msg ON ((msg.USER_FROM_ID = u.id AND msg.USER_TO_ID = :userId) OR
//                              (msg.USER_FROM_ID = :userId AND msg.USER_TO_ID = u.id))
//    AND msg.DATE_DELETED IS NULL
//    WHERE r.STATUS = 'FRIENDS'
//    GROUP BY u.id,
//    u.LAST_NAME,
//    u.FIRST_NAME
//) DATA
//    LEFT JOIN MESSAGE m ON DATA.max_msgId = m.ID
//    ORDER BY m.DATE_SENT DESC, DATA.user_lastName ASC



}
