package com.findme.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Post.class)
public class Post_ {
    public static volatile SingularAttribute<Post, User> userPosted;
    public static volatile SingularAttribute<Post, User> userPagePosted;
}
