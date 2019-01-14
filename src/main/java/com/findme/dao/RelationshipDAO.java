package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;

import java.util.List;

public interface RelationshipDAO{

    void addRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError;
    void deleteRelationship(Long userFromId, Long userToId) throws InternalServerError;
    void updateRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError;


    List<User> getIncomingRequests(String userId) throws InternalServerError;
    List<User> getOutgoingRequests(String userId) throws InternalServerError;
    List<User> getFriendsList(String userId) throws InternalServerError;
    List<User> getSmallFriendsList(String userId) throws InternalServerError;
    Long getFriendsCount(String userId) throws InternalServerError;

    Relationship getRelationship(String userFromId, String userToId) throws InternalServerError;
}
