package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.models.Post;

public interface PostService{

    Post save(Post post) throws BadRequestException;
    Post update(Post post) throws BadRequestException;
    void delete(Long id);
    Post findById(Long id) throws BadRequestException;
}
