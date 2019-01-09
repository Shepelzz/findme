package com.findme.dao;

import com.findme.exception.InternalServerError;
import com.findme.models.Relationship;
import com.findme.models.User;
import com.findme.types.RelationshipStatus;

import java.util.List;

public interface RelationshipDAO extends GeneralDAO<Relationship>{

    RelationshipStatus getRelationshipStatus(User user, User friend) throws InternalServerError;

//    void sendFriendRequest(User user, User friend);
//    void acceptFriendRequest(User user, User friend);
//    void declineFriendRequest(User user, User friend);
//
//    void removeFriend(User user, User friend);
//
    List<User> getIncomingRequests(User user) throws InternalServerError;
    List<User> getOutgoingRequests(User user) throws InternalServerError;



}
