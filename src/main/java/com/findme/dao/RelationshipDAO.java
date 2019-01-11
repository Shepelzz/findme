package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;

import java.util.List;

public interface RelationshipDAO{

    void addRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError;
    void deleteRelationship(String userFromId, String userToId) throws InternalServerError;
    void updateRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError;

    RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError;
    List<User> getIncomingRequests(String userId) throws InternalServerError;
    List<User> getOutgoingRequests(String userId) throws InternalServerError;
    List<User> getFriendsList(String userId) throws InternalServerError;
    List<User> getSmallFriendsList(String userId) throws InternalServerError;
    Long getFriendsCount(String userId) throws InternalServerError;


}
