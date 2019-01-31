package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import com.findme.utils.relationshipValidator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;


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
    @Transactional
    public RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError, BadRequestException {
        validateIncomingParams(userFromId, userToId, null);

        Relationship relationship = relationshipDAO.getRelationship(userFromId, userToId);
        if(relationship == null)
            return null;

//        if(relationship.getStatus() == RelationshipStatus.REQUESTED && !relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
//            return null;
//
//        if(relationship.getStatus() == RelationshipStatus.REJECTED)
//            if(relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
//                return null;
        return relationship.getStatus();
    }

    @Override
    @Transactional
    public void saveRelationship(String userFromId, String userToId) throws InternalServerError, BadRequestException{
        validateIncomingParams(userFromId, userToId, null);

        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);

        if(rel != null)
            throw new BadRequestException("Relationship save - failed. There is an active relationship");

        relationshipDAO.saveRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUESTED);
    }

    @Override
    @Transactional
    public void updateRelationship(String userFromId, String userToId, String status) throws InternalServerError, BadRequestException{
        validateIncomingParams(userFromId, userToId, status);

        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);
        User userTo = userDAO.findById(Long.valueOf(userToId));
        if(userTo == null || rel == null)
            throw new BadRequestException("Relationship save - failed.");
        validateRelationshipUpdate(rel.getStatus(), RelationshipStatus.valueOf(status));

        relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.valueOf(status));
    }

    private void validateIncomingParams(String userFromId, String userToId, String status) throws BadRequestException{
        try{
            Optional.of(userFromId).map(Long::valueOf);
            Optional.of(userToId).map(Long::valueOf);
            Optional.ofNullable(status).map(RelationshipStatus::valueOf);
        } catch (IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        }
    }

    private void validateRelationshipUpdate(RelationshipStatus oldStatus, RelationshipStatus newStatus) throws BadRequestException{
        AbstractRelationshipValidator friendsVal = new FriendsStatusValidator();
        AbstractRelationshipValidator canceledVal = new CanceledStatusValidator();
        AbstractRelationshipValidator deletedVal = new DeletedStatusValidator();
        AbstractRelationshipValidator rejectedVal = new RejectedStatusValidator();
        AbstractRelationshipValidator requestedVal = new RequestedStatusValidator();

        friendsVal.setNextAbstractChainValidator(canceledVal);
        canceledVal.setNextAbstractChainValidator(deletedVal);
        deletedVal.setNextAbstractChainValidator(rejectedVal);
        rejectedVal.setNextAbstractChainValidator(requestedVal);

        friendsVal.check(oldStatus, newStatus);
    }
}
