package com.findme.dao.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.types.RelationshipStatus;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Log4j
@Repository
public class  RelationshipDAOImpl implements RelationshipDAO{
    private static final String SQL_ADD_NEW_RELATIONSHIP = "INSERT INTO RELATIONSHIP(USER_FROM_ID, USER_TO_ID, STATUS, DATE_MODIFIED) VALUES (:userFromId, :userToId, :status, :dateModified)";
    private static final String SQL_UPDATE_RELATIONSHIP = "UPDATE RELATIONSHIP SET USER_FROM_ID = :userFromId_new, USER_TO_ID = :userToId_new, STATUS = :status, DATE_MODIFIED = :dateModified WHERE USER_FROM_ID = :userFromId_old AND USER_TO_ID = :userToId_old";

    private static final String SQL_GET_INCOMING_REQ = "SELECT r.userFrom FROM Relationship r WHERE r.status = 'REQUESTED' AND r.userTo.id = :userId";
    private static final String SQL_GET_OUTGOING_REQ = "SELECT r.userTo FROM Relationship r WHERE r.status = 'REQUESTED' AND r.userFrom.id = :userId";
    private static final String SQL_GET_FRIENDS = "" +
            "SELECT u" +
            " FROM User u, Relationship r" +
            " WHERE r.status = 'FRIENDS' AND ((r.userFrom.id = :userId AND r.userTo.id = u.id) OR (r.userTo.id = :userId AND r.userFrom.id = u.id))";
    private static final String SQL_GET_RELATIONSHIP = "" +
            "SELECT r FROM Relationship r " +
            "WHERE (r.userFrom.id = :userFromId AND r.userTo.id = :userToId) " +
            "OR (r.userTo.id = :userFromId AND r.userFrom.id = :userToId)";

    private static final String SQL_GET_FRIENDS_COUNT = "SELECT COUNT(r) AS cnt FROM Relationship r WHERE r.status = 'FRIENDS' AND (r.userFrom.id = :userId OR r.userTo.id = :userId)";
    private static final String SQL_GET_OUTGOING_REQ_COUNT = "SELECT COUNT(r) FROM Relationship r WHERE r.status = 'REQUESTED' AND r.userFrom.id = :userId";
    private static final String SQL_GET_FRIENDS_LIST_BY_IDS = "SELECT u" +
            " FROM User u, Relationship r" +
            " WHERE r.status = 'FRIENDS' AND ((r.userFrom.id = :userId AND r.userTo.id = u.id) OR (r.userTo.id = :userId AND r.userFrom.id = u.id))" +
            " AND u.id IN (:friendsIdList)";
    private static final String SQL_GET_VALID_USERS_COUNT_BY_IDS = "SELECT COUNT(u) FROM User u WHERE u.id IN (:userIdList)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError {
        log.info("Save new relationship from user "+userFromId+" to user "+userToId);
        try {
            int res = entityManager.createNativeQuery(SQL_ADD_NEW_RELATIONSHIP)
                    .setParameter("userFromId", userFromId)
                    .setParameter("userToId", userToId)
                    .setParameter("status", status.toString())
                    .setParameter("dateModified", new Date())
                    .executeUpdate();

        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
        public void updateRelationship(Long userFromId_old, Long userToId_old, Long userFromId_new, Long userToId_new, RelationshipStatus status) throws InternalServerError {
        log.info("Update relationship from user "+userFromId_old+"->"+userFromId_new+" to user "+userToId_old+"->"+userToId_new+" with status ["+status+"]");
        try {
            int res = entityManager.createNativeQuery(SQL_UPDATE_RELATIONSHIP)
                    .setParameter("userFromId_old", userFromId_old)
                    .setParameter("userToId_old", userToId_old)
                    .setParameter("userFromId_new", userFromId_new)
                    .setParameter("userToId_new", userToId_new)
                    .setParameter("status", status.toString())
                    .setParameter("dateModified", new Date())
                    .executeUpdate();
        }catch (Exception e){
            log.error(e.getMessage(), e);
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
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getIncomingRequests(String userId) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_INCOMING_REQ, User.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getOutgoingRequests(String userId) throws InternalServerError{
        try {
            return entityManager.createQuery(SQL_GET_OUTGOING_REQ, User.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getFriendsList(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getSmallFriendsList(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS, User.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .setMaxResults(6)
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int getFriendsCount(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS_COUNT, Long.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .getSingleResult().intValue();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int getOutgoingRequestsCount(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_OUTGOING_REQ_COUNT, Long.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .getSingleResult().intValue();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public int getValidFriendsCountByIdList(List<Long> ids) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_VALID_USERS_COUNT_BY_IDS, Long.class)
                    .setParameter("userIdList", ids)
                    .getSingleResult().intValue();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<User> getFriendsByIdList(Long userId, List<Long> friendsIds) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_GET_FRIENDS_LIST_BY_IDS, User.class)
                    .setParameter("userId", userId)
                    .setParameter("friendsIdList", friendsIds)
                    .getResultList();
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
