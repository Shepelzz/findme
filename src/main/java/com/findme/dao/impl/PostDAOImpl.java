package com.findme.dao.impl;

import com.findme.dao.PostDAO;
import com.findme.entity.Post;
import com.findme.exception.InternalServerError;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDAOImpl extends GeneralDAOImpl<Post> implements PostDAO {
    private static final String SQL_POST_LIST = "SELECT p FROM Post p WHERE p.userPagePosted.id = :userId ORDER BY p.datePosted DESC";

    public PostDAOImpl() {
        setClazz(Post.class);
    }

    @Override
    public List<Post> getPostList(String userId) throws InternalServerError {
        try {
            return entityManager.createQuery(SQL_POST_LIST, Post.class)
                    .setParameter("userId", Long.valueOf(userId))
                    .setMaxResults(10)
                    .getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
