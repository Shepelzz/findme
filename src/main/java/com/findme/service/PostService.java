package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.PostInfo;

import java.util.List;

public interface PostService{

    Post save(PostInfo postInfo) throws InternalServerError, BadRequestException;
    Post update(Post post) throws InternalServerError, BadRequestException;
    void delete(Long id) throws InternalServerError;
    Post findById(Long id) throws InternalServerError, NotFoundException;

    List<Post> getPostsByFilter(String userId, FilterPagePosts filter) throws BadRequestException, InternalServerError;
    List<Post> getNewsList(Long userId, int maxResults) throws InternalServerError;
}
