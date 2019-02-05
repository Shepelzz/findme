package com.findme.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "POST")
@Getter @Setter @ToString
@EqualsAndHashCode(callSuper = true)

public class Post extends GeneralModel{

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @Column(name = "LOCATION")
    private String location;

    @ManyToOne
    @JoinColumn(name="USER_POSTED_ID", nullable = false)
    private User userPosted;

    @ManyToOne
    @JoinColumn(name="USER_PAGE_POSTED_ID", nullable = false)
    private User userPagePosted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="USERS_TAGGED", joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> usersTagged;

    public void addTaggedUser(User user) {
        usersTagged.add(user);
    }

    public void removeTag(User user) {
        usersTagged.remove(user);
    }


    //TODO
    //levels permissions

    //TODO
    //comments


}
