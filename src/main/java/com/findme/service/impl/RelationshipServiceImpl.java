package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
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
    @Transactional
    public RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError {
        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);
        if(relationship == null)
            return null;

        if(relationship.getStatus() == RelationshipStatus.REQUESTED && !relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
            return null;

        if(relationship.getStatus() == RelationshipStatus.REJECTED)
            if(!relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
                return null;
        return relationship.getStatus();
    }

    @Override
    @Transactional
    public void relationshipSave(String userFromId, String userToId) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        Long userFromIdL, userToIdL;
        try{
            userFromIdL = Long.valueOf(userFromId);
            userToIdL = Long.valueOf(userToId);
        } catch (NumberFormatException e){
            throw new InternalServerError(e.getMessage());
        }
        if(rel == null ){
            relationshipDAO.saveRelationship(userFromIdL, userToIdL, RelationshipStatus.REQUESTED);
        }
        else if (rel.getStatus() == RelationshipStatus.REQUESTED){
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.FRIENDS);
        }
        else if (rel.getStatus() == RelationshipStatus.REJECTED || rel.getStatus() == RelationshipStatus.DELETED || rel.getStatus() == RelationshipStatus.CANCELED){
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.REQUESTED);
        }
    }

    @Override
    @Transactional
    public void relationshipUpdate(String userFromId, String userToId, String status) throws InternalServerError {
        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        Long userFromIdL, userToIdL;
        try{
            userFromIdL = Long.valueOf(userFromId);
            userToIdL = Long.valueOf(userToId);
        } catch (NumberFormatException e){
            throw new InternalServerError(e.getMessage());
        }

        switch(RelationshipStatus.valueOf(status)) {
            case FRIENDS: {
                if (rel != null && rel.getStatus() == RelationshipStatus.REQUESTED)
                    relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.FRIENDS);
                break;
            }
            case DELETED: {
                if(rel != null && rel.getStatus()==RelationshipStatus.FRIENDS)
                    relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.DELETED);
                break;
            }
            case REJECTED: {
                if(rel != null && rel.getStatus()==RelationshipStatus.REQUESTED)
                    relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), rel.getUserFrom().getId(), rel.getUserTo().getId(), RelationshipStatus.REJECTED);
                break;
            }
            case CANCELED: {
                if(rel != null && rel.getStatus()==RelationshipStatus.REQUESTED)
                    relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.CANCELED);
                break;
            }
        }
    }
}
