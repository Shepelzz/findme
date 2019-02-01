package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import com.findme.utils.validator.params.RelationshipValidatorParams;
import com.findme.utils.validator.relationshipValidator.*;
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
        return relationship == null ? null : relationship.getStatus();
    }

    @Override
    @Transactional
    public void saveRelationship(String userFromId, String userToId) throws InternalServerError, BadRequestException{
        validateRelationshipSave(userFromId, userToId);
        relationshipDAO.saveRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUESTED);
    }

    @Override
    @Transactional
    public void updateRelationship(String userFromId, String userToId, String status) throws InternalServerError, BadRequestException{
        Relationship currentRelationship = relationshipDAO.getRelationship(userFromId, userToId);
        validateRelationshipUpdate(currentRelationship, userFromId, userFromId, status);
        relationshipDAO.updateRelationship(
                currentRelationship.getUserFrom().getId(), currentRelationship.getUserTo().getId(),
                Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.valueOf(status));
    }

    private void validateRelationshipUpdate(Relationship currentRelationship, String userFromId, String userToId, String status) throws BadRequestException, InternalServerError{
        validateIncomingParams(userFromId, userToId, status);

        User userTo = userDAO.findById(Long.valueOf(userToId));
        if(userTo == null || currentRelationship == null)
            throw new BadRequestException("Relationship save - failed. Wrong data.");

        AbstractRelationshipValidator friendsVal = new FriendsStatusValidator();
        AbstractRelationshipValidator canceledVal = new CanceledStatusValidator();
        AbstractRelationshipValidator deletedVal = new DeletedStatusValidator();
        AbstractRelationshipValidator rejectedVal = new RejectedStatusValidator();
        AbstractRelationshipValidator requestedVal = new RequestedStatusValidator();

        friendsVal.setNextAbstractChainValidator(canceledVal);
        canceledVal.setNextAbstractChainValidator(deletedVal);
        deletedVal.setNextAbstractChainValidator(rejectedVal);
        rejectedVal.setNextAbstractChainValidator(requestedVal);

        friendsVal.check(RelationshipValidatorParams.builder()
                .oldStatus(currentRelationship.getStatus())
                .newStatus(RelationshipStatus.valueOf(status))
                .relationshipDateModified(currentRelationship.getDateModified())
                .friendsCnt(relationshipDAO.getFriendsCount(userFromId))
                .outgoingReqCnt(relationshipDAO.getOutgoingRequestsCount(userFromId))
                .build());
    }

    private void validateRelationshipSave(String userFromId, String userToId) throws BadRequestException, InternalServerError{
        validateIncomingParams(userFromId, userToId, null);

        if(relationshipDAO.getRelationship(userFromId, userToId) != null)
            throw new BadRequestException("Relationship save - failed. There is an active relationship");

        AbstractRelationshipValidator requestedVal = new RequestedStatusValidator();
        requestedVal.check(RelationshipValidatorParams.builder()
                .oldStatus(RelationshipStatus.DELETED)
                .newStatus(RelationshipStatus.REQUESTED)
                .friendsCnt(relationshipDAO.getFriendsCount(userFromId))
                .outgoingReqCnt(relationshipDAO.getOutgoingRequestsCount(userFromId))
                .build());
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
}
