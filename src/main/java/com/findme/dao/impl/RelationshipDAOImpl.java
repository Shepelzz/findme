package com.findme.dao.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RelationshipDAOImpl extends GeneralDAOImpl<Relationship> implements RelationshipDAO {
    private static final String SQL_ADD_NEW_RELATIONSHIP = "INSERT INTO RELATIONSHIP(USER_FROM_ID, USER_TO_ID, STATUS) VALUES (:userFromId, :userToId, :status)";
    private static final String SQL_DELETE_NEW_RELATIONSHIP = "DELETE FROM RELATIONSHIP WHERE USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId";
    private static final String SQL_UPDATE_NEW_RELATIONSHIP = "UPDATE RELATIONSHIP SET STATUS = :status WHERE USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId";

    private static final String SQL_GET_RELATIONSHIP_STATUS = "SELECT r.STATUS FROM RELATIONSHIP r WHERE r.USER_FROM_ID = :userFromId AND r.USER_TO_ID = :userToId";
    private static final String SQL_GET_INCOMING_REQ = "SELECT u.* FROM RELATIONSHIP r LEFT JOIN USERS u ON r.USER_FROM_ID = u.USER_ID WHERE r.STATUS = :status AND r.USER_TO_ID = :userToId";
    private static final String SQL_GET_OUTGOING_REQ = "SELECT u.* FROM RELATIONSHIP r LEFT JOIN USERS u ON r.USER_TO_ID = u.USER_ID WHERE r.STATUS = :status AND r.USER_FROM_ID = :userFromId";
    private static final String SQL_GET_FRIENDS = "SELECT u.* FROM RELATIONSHIP r LEFT JOIN USERS u ON r.USER_TO_ID = u.USER_ID WHERE r.STATUS = :status AND r.USER_FROM_ID = :userFromId ORDER BY u.LAST_NAME";
    private static final String SQL_GET_FRIENDS_COUNT = "SELECT COUNT(r.USER_TO_ID) AS cnt FROM RELATIONSHIP r WHERE r.STATUS = :status AND r.USER_FROM_ID = :userFromId";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_ADD_NEW_RELATIONSHIP)
                    .setParameter("userFromId", Long.valueOf(userFromId))
                    .setParameter("userToId", Long.valueOf(userToId))
                    .setParameter("status", status.toString())
                    .executeUpdate();

        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteRelationship(String userFromId, String userToId) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_DELETE_NEW_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .executeUpdate();

        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_UPDATE_NEW_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .setParameter("status", status.toString())
                    .executeUpdate();

        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError{
        try {
            RelationshipStatus result = RelationshipStatus.NOT_FRIENDS;
            List<String> statusList = entityManager.createNativeQuery(SQL_GET_RELATIONSHIP_STATUS)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .getResultList();
            if(statusList != null && statusList.size() > 0)
                result = RelationshipStatus.valueOf(statusList.get(0));
            return result;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getIncomingRequests(String userId) throws InternalServerError{
        try {
            return (List<User>) entityManager.createNativeQuery(SQL_GET_INCOMING_REQ)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT.toString())
                    .setParameter("userToId", userId)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getOutgoingRequests(String userId) throws InternalServerError{
        try {
            return (List<User>) entityManager.createNativeQuery(SQL_GET_OUTGOING_REQ)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT.toString())
                    .setParameter("userFromId", userId)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getFriendsList(String userId) throws InternalServerError {
        try {
            return (List<User>) entityManager.createNativeQuery(SQL_GET_FRIENDS)
                    .setParameter("status", RelationshipStatus.FRIENDS.toString())
                    .setParameter("userFromId", userId)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getSmallFriendsList(String userId) throws InternalServerError {
        try {
            return (List<User>) entityManager.createNativeQuery(SQL_GET_FRIENDS)
                    .setParameter("status", RelationshipStatus.FRIENDS.toString())
                    .setParameter("userFromId", userId)
                    .setMaxResults(6)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long getFriendsCount(String userId) throws InternalServerError {
        try {
            return ((Number) entityManager.createNativeQuery(SQL_GET_FRIENDS_COUNT)
                    .setParameter("status", RelationshipStatus.FRIENDS.toString())
                    .setParameter("userFromId", userId)
                    .getSingleResult()).longValue();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
