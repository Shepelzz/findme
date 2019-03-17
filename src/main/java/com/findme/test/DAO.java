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

}
