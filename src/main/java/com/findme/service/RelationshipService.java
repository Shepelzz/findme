package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.types.RelationshipStatus;

public interface RelationshipService {
    RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError, BadRequestException;

    void saveRelationship(String userFromId, String userToId) throws InternalServerError, BadRequestException;
    void updateRelationship(String userFromId, String userToId, String status) throws InternalServerError, BadRequestException;

}
