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
public class RelationshipDAOImpl implements RelationshipDAO{
//TODO check
    private static final String SQL_ADD_NEW_RELATIONSHIP = "INSERT INTO RELATIONSHIP(USER_FROM_ID, USER_TO_ID, STATUS) VALUES (:userFromId, :userToId, :status)";
    private static final String SQL_DELETE_RELATIONSHIP = "DELETE FROM RELATIONSHIP WHERE USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId";
    private static final String SQL_UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET STATUS = :status WHERE USER_FROM_ID = :userFromId AND USER_TO_ID = :userToId";

    private static final String SQL_GET_INCOMING_REQ = "SELECT r.userFrom FROM Relationship r WHERE r.status = :status AND r.userTo.id = :userId";
    private static final String SQL_GET_OUTGOING_REQ = "SELECT r.userTo FROM Relationship r WHERE r.status = :status AND r.userFrom.id = :userId";
    private static final String SQL_GET_FRIENDS = "" +
            "SELECT u FROM User u " +
            "WHERE u IN (" +
            "   SELECT r.userTo FROM Relationship r WHERE r.status = :status AND r.userFrom.id = :userId " +
            ") OR u IN (" +
            "   SELECT r.userFrom FROM Relationship r WHERE r.status = :status AND r.userTo.id = :userId " +
            ") ORDER BY u.lastName";
    private static final String SQL_GET_FRIENDS_COUNT = "SELECT COUNT(r) AS cnt FROM Relationship r WHERE r.status = :status AND (r.userFrom.id = :userId OR r.userTo.id = :userId)";

    private static final String SQL_GET_RELATIONSHIP = "" +
            "SELECT r FROM Relationship r " +
            "WHERE (r.userFrom.id = :userFromId AND r.userTo.id = :userToId) " +
            "OR (r.userTo.id = :userFromId AND r.userFrom.id = :userToId)";


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_ADD_NEW_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .setParameter("status", status.toString())
                    .executeUpdate();

        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_DELETE_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .executeUpdate();

        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError {
        try {
            int res = entityManager.createNativeQuery(SQL_UPDATE_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .setParameter("status", status.toString())
                    .executeUpdate();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Relationship getRelationship(String userFromId, String userToId) throws InternalServerError{
        try {
            Relationship result = null;
            List<Relationship> resultList = entityManager.createQuery(SQL_GET_RELATIONSHIP, Relationship.class)
                    .setParameter("userFromId", Long.valueOf(userFromId))
                    .setParameter("userToId", Long.valueOf(userToId))
                    .getResultList();
            if(resultList != null && resultList.size() > 0)
                result = resultList.get(0);
            return result;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getIncomingRequests(String userId) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_INCOMING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getOutgoingRequests(String userId) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_OUTGOING_REQ, User.class)
                    .setParameter("status", RelationshipStatus.REQUEST_SENT)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getFriendsList(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getSmallFriendsList(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userId", Long.valueOf(userId))
                    .setMaxResults(6)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Long getFriendsCount(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS_COUNT, Long.class)
                    .setParameter("status", RelationshipStatus.FRIENDS)
                    .setParameter("userId", Long.valueOf(userId))
                    .getSingleResult();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
