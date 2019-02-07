package com.findme.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @ToString
public class PostInfo {

    private String message;
    private String location;
    private String userPostedId;
    private String userPagePostedId;
    private List<Long> usersTaggedIds;

}
