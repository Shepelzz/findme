package com.findme.dao.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RelationshipDAOImpl extends GeneralDAOImpl<Relationship> implements RelationshipDAO {
    public static final String SQL_GET_RELATIONSHIP_STATUS = "";
    public static final String SQL_GET_INCOMING_REQ = "SELECT u FROM Relationship r LEFT JOIN User u ON r.userFrom.id = u.id WHERE r.status = :status AND r.userTo = :userTo";
    public static final String SQL_GET_OUTGOING_REQ = "SELECT u FROM Relationship r LEFT JOIN User u ON r.userTo.id = u.id WHERE r.status = :status AND r.userFrom = :userFrom";

    @Override
    public RelationshipStatus getRelationshipStatus(User user, User friend) {
        return null;
    }

    @Override
    public List<User> getIncomingRequests(User user) throws InternalServerError{
        try {
            List<User> userList = entityManager.createQuery(SQL_GET_INCOMING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userTo", user)
                    .getResultList();
            return userList;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getOutgoingRequests(User user) throws InternalServerError{
        try {
            List<User> userList = entityManager.createQuery(SQL_GET_OUTGOING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userFrom", user)
                    .getResultList();
            return userList;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
