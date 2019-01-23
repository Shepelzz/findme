package com.findme.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "POST")
@Getter
@Setter
@ToString
public class Post extends GeneralModel{

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @ManyToOne
    @JoinColumn(name="USER_POSTED_ID", nullable = false)
    private User userPosted;
    //TODO
    //levels permissions

    //TODO
    //comments

}
