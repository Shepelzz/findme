package com.findme.service.impl;

import com.findme.dao.PostDAO;
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
    public Post save(Post post) {
        return postDAO.save(post);
    }

    @Override
    @Transactional
    public Post update(Post post) {
        return postDAO.update(post);
    }

    @Override
    public void delete(Long id) {
        postDAO.delete(id);
    }
}
