package com.findme.utils.validator.params;

import com.findme.model.Relationship;
import com.findme.model.User;
import com.findme.model.PostInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class PostValidatorParams {

    private PostInfo postInfo;
    private Relationship relBtwAuthorAndPagePostedUser;
    private List<User> usersTagged;

}
