package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.Post;
import com.findme.model.PostInfo;

public interface PostService{

    Post save(PostInfo postInfo) throws InternalServerError, BadRequestException;
    Post update(Post post) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError;
    Post findById(Long id) throws InternalServerError, NotFoundException;
}
