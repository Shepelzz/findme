package com.findme.service.impl;

import com.findme.dao.PostDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.*;
import com.findme.service.PostService;
import com.findme.utils.validator.params.PostValidatorParams;
import com.findme.utils.validator.postValidator.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j
@Service
public class PostServiceImpl implements PostService {
    private PostDAO postDAO;
    private UserDAO userDAO;
    private RelationshipDAO relationshipDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO, UserDAO userDAO, RelationshipDAO relationshipDAO) {
        this.postDAO = postDAO;
        this.userDAO = userDAO;
        this.relationshipDAO = relationshipDAO;

    }

    @Override
    public Post save(PostInfo postInfo) throws InternalServerError, BadRequestException {
        validateIncomingParams(postInfo.getUserPostedId(), postInfo.getUserPagePostedId());

        User userPosted = userDAO.findById(Long.valueOf(postInfo.getUserPostedId()));
        User userPagePosted = userDAO.findById(Long.valueOf(postInfo.getUserPagePostedId()));
        Relationship relBtwAuthorAndPagePostedUser = relationshipDAO.getRelationship(String.valueOf(postInfo.getUserPostedId()), String.valueOf(postInfo.getUserPagePostedId()));
        List<User> usersTagged = null;
        if(postInfo.getUsersTaggedIds() != null && postInfo.getUsersTaggedIds().size() > 0)
            usersTagged = relationshipDAO.getFriendsByIdList(userPosted.getId(), postInfo.getUsersTaggedIds());

        validatePostInfo(
            PostValidatorParams.builder()
                    .postInfo(postInfo)
                    .relBtwAuthorAndPagePostedUser(relBtwAuthorAndPagePostedUser)
                    .usersTagged(usersTagged)
                    .build()
        );

        Post post = new Post();
            post.setUserPosted(userPosted);
            post.setUserPagePosted(userPagePosted);
            post.setDatePosted(new Date());
            post.setLocation(postInfo.getLocation());
            post.setMessage(postInfo.getMessage());
            post.setUsersTagged(usersTagged);

        return postDAO.save(post);
    }

    @Override
    public Post update(Post post) throws InternalServerError {
        return postDAO.update(post);
    }

    @Override
    public void delete(Long id, Long userId) throws InternalServerError, BadRequestException {
        Post post = postDAO.findById(id);
        if(post == null) {
            log.warn("Post delete fail. Wrong post id ["+id+"]");
            throw new BadRequestException("Wrong post id");
        }
        if(!post.getUserPosted().getId().equals(userId)) {
            log.warn("Post delete fail. User ["+userId+"] can not remove post ["+id+"]");
            throw new BadRequestException("You can not remove this post");
        }
        postDAO.delete(id);
    }

    @Override
    public Post findById(Long id) throws InternalServerError, NotFoundException {
        return postDAO.findById(id);
    }

    @Override
    public List<Post> getPostsByFilter(Long userId, FilterPagePosts filter) throws BadRequestException, InternalServerError {
        try{
            return postDAO.getPostsByFilter(userId, filter);
        } catch (NumberFormatException e){
            log.warn(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<Post> getNewsList(Long userId, int maxResults, int currentListPart) throws InternalServerError {
        int rowsFrom = currentListPart == 1 ? 0 : currentListPart*maxResults-maxResults;
        return postDAO.getNewsListPart(userId, rowsFrom, maxResults);
    }

    private void validateIncomingParams(String userFromId, String userToId) throws BadRequestException{
        try{
            Optional.of(userFromId).map(Long::valueOf);
            Optional.of(userToId).map(Long::valueOf);
        } catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    private void validatePostInfo(PostValidatorParams params) throws BadRequestException{
        log.info("Start post validation");

        AbstractPostValidator usersValidator = new UserPagePostedValidator();
        AbstractPostValidator messageValidator = new MessageValidator();
        AbstractPostValidator locationValidator = new LocationValidator();
        AbstractPostValidator usersTaggedValidator = new UserTaggedValidator();

        usersValidator.setNextAbstractChainValidator(messageValidator);
        messageValidator.setNextAbstractChainValidator(locationValidator);
        locationValidator.setNextAbstractChainValidator(usersTaggedValidator);

        usersValidator.check(params);
    }
}
