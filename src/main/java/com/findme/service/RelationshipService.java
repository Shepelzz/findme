package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.types.RelationshipStatus;

public interface RelationshipService {
    RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError;

    void addFriend(String userFromId, String userToId) throws InternalServerError;
    void deleteFriend(String userFromId, String userToId) throws InternalServerError;

    void acceptFriend(String userFromId, String userToId) throws InternalServerError, BadRequestException;
    void rejectFriend(String userFromId, String userToId) throws InternalServerError;
    void cancelRequest(String userFromId, String userToId) throws InternalServerError;
}
