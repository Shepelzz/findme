package com.findme.dao.impl;

import com.findme.dao.PostDAO;
import com.findme.exception.InternalServerError;
import com.findme.model.FilterPagePosts;
import com.findme.model.Post;
import com.findme.model.Post_;
import com.findme.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class PostDAOImpl extends GeneralDAOImpl<Post> implements PostDAO {
    private static final String SQL_POST_LIST = "SELECT p FROM Post p WHERE p.userPagePosted.id = :userId ORDER BY p.datePosted DESC";
    private static final String SQL_NEWS_LIST = "SELECT p" +
            " FROM Post p" +
            " LEFT JOIN Relationship r ON (r.userFrom.id = :userId AND r.userTo.id = p.userPosted.id) OR (r.userTo.id = :userId AND r.userFrom.id = p.userPosted.id)" +
            " WHERE r.status = 'FRIENDS'" +
            " ORDER BY p.datePosted DESC";

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
    public List<Post> getPostsByFilter(Long userId, FilterPagePosts filter) throws InternalServerError {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Post> criteriaQuery = cb.createQuery(Post.class);
            Root<Post> postRoot = criteriaQuery.from(Post.class);
            Join<Post, User> userPosted = postRoot.join(Post_.userPosted);
            Join<Post, User> userPagePosted = postRoot.join(Post_.userPagePosted);

            Predicate criteria = cb.conjunction();
            //user_id
            criteria = cb.and (criteria, cb.equal(userPagePosted.get("id"), userId));
            //ownerPosts
            if(filter.isOwnerPosts())
                criteria = cb.and(criteria, cb.equal(userPosted.get("id"), userId));
            //friendsPosts
            if(filter.isFriendsPosts())
                criteria = cb.and(criteria, cb.notEqual(userPosted.get("id"), userId));
            //usersPostedIds
            if(filter.getUserPostedId() != null)
                criteria = cb.and(criteria, cb.equal(userPosted.get("id"), filter.getUserPostedId()));

            criteriaQuery.select(postRoot).where(criteria);
            criteriaQuery.orderBy(cb.desc(postRoot.get("datePosted")));
            return entityManager.createQuery(criteriaQuery).getResultList();
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }

    @Override
    public List<Post> getNewsListPart(Long userId, int rowsFrom, int maxResults) throws InternalServerError {
        try {

            List<Post> list = entityManager.createQuery(SQL_NEWS_LIST, Post.class)
                    .setParameter("userId", userId)
                    .setFirstResult(rowsFrom)
                    .setMaxResults(maxResults)
                    .getResultList();
            return list;
        }catch (Exception e){
            throw new InternalServerError(e.getMessage(), e.getCause());
        }
    }
}
