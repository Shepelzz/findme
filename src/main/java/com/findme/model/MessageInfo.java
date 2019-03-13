package com.findme.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageInfo {

    private String userFromId;
    private String userToId;
    private String text;
}
