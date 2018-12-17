package com.findme.service;

import com.findme.models.Post;

public interface PostService {

    Post save(Post post);
    Post update(Post post);
    void delete(Long id);

}
