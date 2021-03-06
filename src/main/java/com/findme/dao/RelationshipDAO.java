package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.types.RelationshipStatus;

import java.util.List;

public interface RelationshipDAO{

    void saveRelationship(Long userFromId, Long userToId, RelationshipStatus status) throws InternalServerError;
    void updateRelationship(Long userFromId_old, Long userToId_old, Long userFromId_new, Long userToId_new, RelationshipStatus status) throws InternalServerError;


    List<User> getIncomingRequests(String userId) throws InternalServerError;
    List<User> getOutgoingRequests(String userId) throws InternalServerError;
    List<User> getFriendsList(String userId) throws InternalServerError;
    List<User> getSmallFriendsList(String userId) throws InternalServerError;
    int getFriendsCount(String userId) throws InternalServerError;
    int getOutgoingRequestsCount(String userId) throws InternalServerError;

    int getValidFriendsCountByIdList(List<Long> ids) throws InternalServerError;
    List<User> getFriendsByIdList(Long userId, List<Long> friendsIds) throws InternalServerError;

    Relationship getRelationship(String userFromId, String userToId) throws InternalServerError;
}
