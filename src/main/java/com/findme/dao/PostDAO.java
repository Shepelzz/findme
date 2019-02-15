package com.findme.dao;

import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.exception.InternalServerError;

import java.util.List;

public interface PostDAO extends GeneralDAO<Post>{

    List<Post> getPostList(String userId) throws InternalServerError;
    List<Post> getPostsByFilter(Long userId, FilterPagePosts filter) throws InternalServerError;
}
