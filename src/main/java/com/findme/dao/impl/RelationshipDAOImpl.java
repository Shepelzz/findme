package com.findme.dao.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class RelationshipDAOImpl extends GeneralDAOImpl<Relationship> implements RelationshipDAO {
    public static final String SQL_GET_RELATIONSHIP_STATUS = "SELECT r.status FROM Relationship r WHERE r.userFrom = :userFrom AND r.userTo = :userTo";
    public static final String SQL_GET_INCOMING_REQ = "SELECT u FROM Relationship r LEFT JOIN User u ON r.userFrom.id = u.id WHERE r.status = :status AND r.userTo = :userTo";
    public static final String SQL_GET_OUTGOING_REQ = "SELECT u FROM Relationship r LEFT JOIN User u ON r.userTo.id = u.id WHERE r.status = :status AND r.userFrom = :userFrom";
    public static final String SQL_GET_FRIENDS = "SELECT u FROM Relationship r LEFT JOIN User u ON r.userTo.id = u.id WHERE r.status = :status AND r.userFrom = :userFrom ORDER BY u.lastName";
    public static final String SQL_GET_FRIENDS_COUNT = "SELECT COUNT(r.userTo) AS cnt FROM Relationship r WHERE r.status = :status AND r.userFrom = :userFrom";

    @Override
    public RelationshipStatus getRelationshipStatus(User user, User friend) throws InternalServerError{
        try {
            RelationshipStatus result = RelationshipStatus.NOT_FRIENDS;
            List<RelationshipStatus> statusList = entityManager.createQuery(SQL_GET_RELATIONSHIP_STATUS, RelationshipStatus.class)
                    .setParameter("userFrom", user)
                    .setParameter("userTo", friend)
                    .getResultList();
            if(statusList != null && statusList.size() > 0)
                result = statusList.get(0);
            return result;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    @Transactional
    public void acceptFriendRequest(User user, User friend) throws InternalServerError {

    }

    @Override
    @Transactional
    public void rejectFriendRequest(User user, User friend) throws InternalServerError {

    }

    @Override
    public List<User> getIncomingRequests(User user) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_INCOMING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userTo", user)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getOutgoingRequests(User user) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_OUTGOING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userFrom", user)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getFriendsList(User user) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userFrom", user)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getSmallFriendsList(User user) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userFrom", user)
                    .setMaxResults(6)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long getFriendsCount(User user) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS_COUNT, Long.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userFrom", user)
                    .getSingleResult();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
