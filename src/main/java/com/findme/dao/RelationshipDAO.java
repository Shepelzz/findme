package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;

import java.util.List;

public interface RelationshipDAO extends GeneralDAO<Relationship>{

//    void addRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError;
//    void deleteRelationship(String userFromId, String userToId) throws InternalServerError;
//    void updateRelationship(String userFromId, String userToId, RelationshipStatus status) throws InternalServerError;
//
//    RelationshipStatus getRelationshipStatus(String userFromId, String userToId) throws InternalServerError;
//    List<User> getIncomingRequests(String userId) throws InternalServerError;
//    List<User> getOutgoingRequests(String userId) throws InternalServerError;
//    List<User> getFriendsList(String userId) throws InternalServerError;
//    List<User> getSmallFriendsList(String userId) throws InternalServerError;
//    Long getFriendsCount(String userId) throws InternalServerError;

    RelationshipStatus getRelationshipStatus(User user, User friend) throws InternalServerError;

    //    void sendFriendRequest(User user, User friend);
//    void acceptFriendRequest(User user, User friend);
//    void declineFriendRequest(User user, User friend);
//
//    void removeFriend(User user, User friend);
//
    List<User> getIncomingRequests(User user) throws InternalServerError;
    List<User> getOutgoingRequests(User user) throws InternalServerError;
    List<User> getFriendsList(User user) throws InternalServerError;
    List<User> getSmallFriendsList(User user) throws InternalServerError;


}
