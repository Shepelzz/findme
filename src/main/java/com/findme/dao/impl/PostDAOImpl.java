package com.findme.dao.impl;

import com.findme.dao.PostDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

    @Override
    public List<Post> getPostsByFilter(String userId, FilterPagePosts filter) throws InternalServerError {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Post> criteriaQuery = cb.createQuery(Post.class);
        Root<Post> postRoot = criteriaQuery.from(Post.class);

        Predicate criteria = cb.conjunction();
        //user_id
        criteria = cb.and (criteria, cb.equal(postRoot.get("userPagePosted"), Long.valueOf(userId)));
        //ownerPosts
        if(filter.isOwnerPosts())
            criteria = cb.and(criteria, cb.equal(postRoot.get("userPosted"), Long.valueOf(userId)));
        //friendsPosts
        if(filter.isFriendsPosts())
//            criteria = cb.and(criteria, cb.equal(postRoot.get("userPosted"), Long.valueOf(userId)));
        //usersPostedIds
        if(filter.getUsersPostedIds() != null && filter.getUsersPostedIds().size() > 0)



        criteriaQuery.select(postRoot).where(criteria);

        return null;
    }
}
