package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;
import com.findme.utils.relationshipValidator.CanceledStatusValidator;
import com.findme.utils.relationshipValidator.DeletedStatusValidator;
import com.findme.utils.relationshipValidator.FriendsStatusValidator;
import com.findme.utils.relationshipValidator.RejectedStatusValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;


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

        if(relationship.getStatus() == RelationshipStatus.REQUESTED && !relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
            return null;

        if(relationship.getStatus() == RelationshipStatus.REJECTED)
            if(relationship.getUserFrom().getId().equals(Long.valueOf(userFromId)))
                return null;
        return relationship.getStatus();
    }

    @Override
    @Transactional
    public void relationshipSave(String userFromId, String userToId) throws InternalServerError, BadRequestException{
        validateIncomingParams(userFromId, userToId, null);

        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);

        if(rel == null ){
            relationshipDAO.saveRelationship(Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUESTED);
        }
        else if (rel.getStatus() == RelationshipStatus.REQUESTED){
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.FRIENDS);
        }
        else if (rel.getStatus() == RelationshipStatus.REJECTED || rel.getStatus() == RelationshipStatus.DELETED || rel.getStatus() == RelationshipStatus.CANCELED){
            relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.REQUESTED);
        }
    }

    @Override
    @Transactional
    public void relationshipUpdate(String userFromId, String userToId, String status) throws InternalServerError, BadRequestException{
        validateIncomingParams(userFromId, userToId, status);

        Relationship rel = relationshipDAO.getRelationship(userFromId, userToId);

        //Validate user to id
        User userTo = userDAO.findById(Long.valueOf(userToId));
        if(userTo == null)
            throw new BadRequestException("Relationship process - failed. User with id "+userToId+" was not found");

        AbstractChainValidator<Map<String, Object>> friendsVal = new FriendsStatusValidator();
        AbstractChainValidator<Map<String, Object>> canceledVal = new CanceledStatusValidator();
        AbstractChainValidator<Map<String, Object>> deletedVal = new DeletedStatusValidator();
        AbstractChainValidator<Map<String, Object>> rejectedVal = new RejectedStatusValidator();

        friendsVal.setNextAbstractChainValidator(canceledVal);
        canceledVal.setNextAbstractChainValidator(deletedVal);
        deletedVal.setNextAbstractChainValidator(rejectedVal);

        Map<String, Object> params = new HashMap<>();
        params.put("status", RelationshipStatus.valueOf(status));
        params.put("currentRelationship", rel);

        friendsVal.check(params);
        relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), Long.valueOf(userFromId), Long.valueOf(userToId), RelationshipStatus.valueOf(status));
    }

    private void validateIncomingParams(String userFromId, String userToId, String status) throws BadRequestException{
        try{
            if(userFromId != null)
                Long.valueOf(userFromId);
            if(userToId != null)
                Long.valueOf(userToId);
            if(status != null)
                RelationshipStatus.valueOf(status);
        } catch (IllegalArgumentException e){
            throw new BadRequestException(e.getMessage());
        }
    }
}
