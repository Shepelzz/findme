package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.types.RelationshipStatus;

public interface RelationshipService {
    RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError, BadRequestException;

    void relationshipSave(String userFromId, String userToId) throws InternalServerError, BadRequestException;
    void relationshipUpdate(String userFromId, String userToId, String status) throws InternalServerError, BadRequestException;

//    void relationshipSave(String userFromId, String userToId) throws InternalServerError;
//    void relationshipUpdate(String userFromId, String userToId, String status) throws InternalServerError;
}
