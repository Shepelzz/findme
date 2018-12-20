package com.findme.service.impl;

import com.findme.dao.PostDAO;
import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.Post;
import com.findme.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceImpl implements PostService {
    private PostDAO postDAO;

    @Autowired
    public PostServiceImpl(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    @Override
    @Transactional
    public Post save(Post post) throws BadRequestException {
        validatePost(post);
        return postDAO.save(post);
    }

    @Override
    @Transactional
    public Post update(Post post) throws BadRequestException {
        validatePost(post);
        return postDAO.update(post);
    }

    @Override
    public void delete(Long id) throws InternalServerError {
        postDAO.delete(id);
    }

    @Override
    public Post findById(Long id) throws BadRequestException {
        Post post = postDAO.findById(id);
        if(post == null)
            throw new BadRequestException("Post with id "+id+" was not found");
        return post;
    }

    private void validatePost(Post post) throws BadRequestException{
        if(post.getMessage().equals(""))
            throw new BadRequestException("Message can not be empty");
    }
}
