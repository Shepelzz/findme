package com.findme.dao;

import com.findme.entity.Post;
import com.findme.exception.InternalServerError;

import java.util.List;

public interface PostDAO extends GeneralDAO<Post>{

    List<Post> getPostList(String userId) throws InternalServerError;

}
