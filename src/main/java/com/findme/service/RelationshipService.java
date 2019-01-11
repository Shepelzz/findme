package com.findme.service;

import com.findme.exception.InternalServerError;

public interface RelationshipService {
    void addFriend(String userFromId, String userToId) throws InternalServerError;
    void deleteFriend(String userFromId, String userToId) throws InternalServerError;

    void acceptFriend(String userFromId, String userToId) throws InternalServerError;
    void rejectFriend(String userFromId, String userToId) throws InternalServerError;
    void cancelRequest(String userFromId, String userToId) throws InternalServerError;
}
