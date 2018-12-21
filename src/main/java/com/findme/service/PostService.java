package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.models.Post;

public interface PostService{

    Post save(Post post) throws InternalServerError, BadRequestException;
    Post update(Post post) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError;
    Post findById(Long id) throws InternalServerError, NotFoundException;
}
