package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class RelationshipServiceImpl implements RelationshipService {

    private RelationshipDAO relationshipDAO;
    private UserDAO userDAO;

    @Autowired
    public RelationshipServiceImpl(RelationshipDAO relationshipDAO, UserDAO userDAO) {
        this.relationshipDAO = relationshipDAO;
        this.userDAO = userDAO;
    }


    @Override
    public void addFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel == null) {
            try {
                relationshipDAO.addRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUEST_SENT);
            } catch (NumberFormatException e){
                throw  new InternalServerError(e.getMessage());
            }
        } else if(rel.getStatus() == RelationshipStatus.REQUEST_SENT)
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.FRIENDS);
    }

    @Override
    public void deleteFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.FRIENDS)
            relationshipDAO.deleteRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId());

    }

    @Override
    public void acceptFriend(String userFromId, String userToId) throws InternalServerError, BadRequestException {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.FRIENDS);
    }

    @Override
    public void rejectFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.REQUEST_REJECTED);
    }

    @Override
    public void cancelRequest(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.deleteRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId());
    }
}
