package com.findme.service.impl;

import com.findme.dao.PostDAO;
import com.findme.dao.RelationshipDAO;
import com.findme.dao.UserDAO;
import com.findme.entity.Post;
import com.findme.entity.Relationship;
import com.findme.entity.User;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.PostInfo;
import com.findme.service.PostService;
import com.findme.utils.validator.params.PostValidatorParams;
import com.findme.utils.validator.postValidator.AbstractPostValidator;
import com.findme.utils.validator.postValidator.LocationValidator;
import com.findme.utils.validator.postValidator.MessageValidator;
import com.findme.utils.validator.postValidator.UserPagePostedValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Relationship rel = relationshipDAO.getRelationship(String.valueOf(postInfo.getUserPostedId()), String.valueOf(postInfo.getUserPagePostedId()));
        List<User> usersTagged = relationshipDAO.getFriendsByIdList(postInfo.getUserPostedId(), postInfo.getUsersTaggedIds());

        validatePostInfo(
            PostValidatorParams.builder()
                    .postInfo(postInfo)
                    .relBtwAuthorAndPagePostedUser(rel)
                    .usersTagged(usersTagged)
                    .build()
        );

        Post post = new Post();
        post.setUserPosted(userDAO.findById(postInfo.getUserPostedId()));
        post.setUserPagePosted(userDAO.findById(postInfo.getUserPagePostedId()));
        post.setDatePosted(postInfo.getDatePosted());
        post.setLocation(postInfo.getLocation());
        post.setMessage(postInfo.getMessage());
        post.setUsersTagged(usersTagged);

        return postDAO.save(post);
    }

    @Override
    public Post update(Post post) throws InternalServerError, BadRequestException {
        return postDAO.update(post);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        postDAO.delete(id);
    }

    @Override
    public Post findById(Long id) throws InternalServerError, NotFoundException {
        Post post = postDAO.findById(id);
        if(post == null)
            throw new NotFoundException("Post with id "+id+" was not found");
        return post;
    }

    private void validatePostInfo(PostValidatorParams params) throws BadRequestException{
        AbstractPostValidator usersValidator = new UserPagePostedValidator();
        AbstractPostValidator messageValidator = new MessageValidator();
        AbstractPostValidator locationValidator = new LocationValidator();

        usersValidator.setNextAbstractChainValidator(messageValidator);
        messageValidator.setNextAbstractChainValidator(locationValidator);

        usersValidator.check(params);
    }
}
