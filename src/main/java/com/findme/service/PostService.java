package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.models.Post;

public interface PostService{

    Post save(Post post) throws BadRequestException;
    Post update(Post post) throws BadRequestException;
    void delete(Long id) throws InternalServerError;
    Post findById(Long id) throws BadRequestException;
}
