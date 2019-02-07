package com.findme.utils.validator.params;

import com.findme.entity.Relationship;
import com.findme.entity.User;
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
