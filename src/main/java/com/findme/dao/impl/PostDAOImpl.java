package com.findme.dao.impl;

import com.findme.dao.PostDAO;
import com.findme.model.Post;
import org.springframework.stereotype.Repository;

@Repository
public class PostDAOImpl extends GeneralDAOImpl<Post> implements PostDAO {

    public PostDAOImpl() {
        setClazz(Post.class);
    }

}
