package com.findme.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @Builder
public class FilterPagePosts {

    private boolean ownerPosts;
    private boolean friendsPosts;
    private Long userPostedId;

}
