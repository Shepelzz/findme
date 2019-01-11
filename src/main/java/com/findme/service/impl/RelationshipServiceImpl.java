package com.findme.service.impl;

import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.InternalServerError;
import com.findme.service.RelationshipService;
import com.findme.types.RelationshipStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


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
    /*   Проверяем на наличие запросов от пользователя к нам:
    *    - если нет запросов от пользователя, то создаем новую связь от нас к пользователю со статусом REQUEST_SENT
    *    - если REQUEST_SENT - обновляем на FRIENDS и создаем новую связь от нас к пользователя с таким же статусом
    *    - если REQUEST_REJECTED - то обновляем на NOT_FRIENDS и создаем новую связь от нас к пользователю со статусом REQUEST_SENT
    */
    public void addFriend(String userFromId, String userToId) throws InternalServerError {
        RelationshipStatus status = relationshipDAO.getRelationshipStatus(userToId, userFromId);
        if(status == RelationshipStatus.NOT_FRIENDS)
            relationshipDAO.addRelationship(userFromId, userToId, RelationshipStatus.REQUEST_SENT);
        else{
            switch (status) {
                case REQUEST_SENT: {
                    relationshipDAO.updateRelationship(userToId, userFromId, RelationshipStatus.FRIENDS);
                    relationshipDAO.addRelationship(userFromId, userToId, RelationshipStatus.FRIENDS);
                }
                case REQUEST_REJECTED: {
                    relationshipDAO.updateRelationship(userToId, userFromId, RelationshipStatus.NOT_FRIENDS);
                    relationshipDAO.addRelationship(userFromId, userToId, RelationshipStatus.REQUEST_SENT);
                }
            }
        }
    }

    @Override
    @Transactional
    /*  Связь от нас к пользователю удаляем
    *   Связь от пользователя к нам удаляем
    */
    public void deleteFriend(String userFromId, String userToId) throws InternalServerError {
        relationshipDAO.deleteRelationship(userFromId, userToId);
        relationshipDAO.deleteRelationship(userToId, userFromId);
    }

    @Override
    @Transactional
    /*  Обновляем связь от друга к нам статусом FRIENDS. Если на момент обновления связь NOT_FRIENDS - ничего не делаем
    *   Проверяем если есть связь от нас к другу:
    *       - есть - обновляем на FRIENDS
    *       - нет - создаем новую связь со статусом FRIENDS
    */
    public void acceptFriend(String userFromId, String userToId) throws InternalServerError {
        if(relationshipDAO.getRelationshipStatus(userToId, userFromId) != RelationshipStatus.REQUEST_SENT)
            return;

        relationshipDAO.updateRelationship(userToId, userFromId, RelationshipStatus.FRIENDS);
        if(relationshipDAO.getRelationshipStatus(userFromId, userToId) != RelationshipStatus.NOT_FRIENDS)
            relationshipDAO.updateRelationship(userFromId, userToId, RelationshipStatus.FRIENDS);
        else
            relationshipDAO.addRelationship(userFromId, userToId, RelationshipStatus.FRIENDS);
    }

    @Override
    @Transactional
    /*  Обновляем связь друга к нам статусом REQUEST_REJECTED
    *   Если запрос был отменен, т.е. в статусе NOT_FRIENDS - все равно обновляем на REJECTED
    */
    public void rejectFriend(String userFromId, String userToId) throws InternalServerError {
        relationshipDAO.updateRelationship(userToId, userFromId, RelationshipStatus.REQUEST_REJECTED);
    }

    @Override
    @Transactional
    /*  Удаляем связь от нас к другу
    */
    public void cancelRequest(String userFromId, String userToId) throws InternalServerError {
        relationshipDAO.deleteRelationship(userFromId, userToId);
    }
}
