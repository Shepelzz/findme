package com.findme.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter @Setter @ToString
public class PostInfo {

    private String message;
    private Date datePosted;
    private String location;
    private Long userPostedId;
    private Long userPagePostedId;
    private List<Long> usersTaggedIds;



}
