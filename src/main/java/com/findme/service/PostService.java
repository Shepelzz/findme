package com.findme.service;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.PostInfo;

import java.util.List;

public interface PostService extends GeneralService<Post>{

    Post save(PostInfo postInfo) throws InternalServerError, BadRequestException;
    Post update(Post post) throws InternalServerError, BadRequestException;
    void delete(Long id, Long userId) throws InternalServerError, BadRequestException;

    List<Post> getPostsByFilter(Long userId, FilterPagePosts filter) throws BadRequestException, InternalServerError;
    List<Post> getNewsList(Long userId, int maxResults, int currentListPart) throws InternalServerError;
}
