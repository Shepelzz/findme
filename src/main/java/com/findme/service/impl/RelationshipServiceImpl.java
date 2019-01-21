package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
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

    @Autowired
    public RelationshipServiceImpl(RelationshipDAO relationshipDAO) {
        this.relationshipDAO = relationshipDAO;
    }

    @Override
    public RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError {
        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);
        if(relationship == null)
            return null;

        if(relationship.getStatus() == RelationshipStatus.REQUEST_SENT && !relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
            return null;

        if(relationship.getStatus() == RelationshipStatus.REQUEST_REJECTED)
            if(!relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
                return null;
        return relationship.getStatus();
    }

    @Override
    @Transactional
    public void addFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel == null) {
            try {
                relationshipDAO.addRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUEST_SENT);
            } catch (NumberFormatException e){
                throw  new InternalServerError(e.getMessage());
            }
        } else if(rel.getStatus() == RelationshipStatus.REQUEST_SENT) {
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.FRIENDS);
        } else if(rel.getStatus() == RelationshipStatus.REQUEST_REJECTED) {
            relationshipDAO.deleteRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId());
            relationshipDAO.addRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUEST_SENT);
        }
    }

    @Override
    @Transactional
    public void deleteFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.FRIENDS)
            relationshipDAO.deleteRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId());

    }

    @Override
    @Transactional
    public void acceptFriend(String userFromId, String userToId) throws InternalServerError, BadRequestException {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.FRIENDS);
    }

    @Override
    @Transactional
    public void rejectFriend(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.REQUEST_REJECTED);
    }

    @Override
    @Transactional
    public void cancelRequest(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        if(rel != null && rel.getStatus()==RelationshipStatus.REQUEST_SENT)
            relationshipDAO.deleteRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId());
    }
}
