package com.findme.test;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter @Setter @Builder @ToString
public class ConversationInfo {
    private int userId;
    private String userFirstName;
    private String userLastName;
    private int messageId;
    private String messageText;
    private int messageUserFromId;
    private int messageUserToId;
    private Date messageDateSent;
    private Date messageDateRead;

    public ConversationInfo(int userId, String userFirstName, String userLastName, int messageId, String messageText, int messageUserFromId, int messageUserToId, Date messageDateSent, Date messageDateRead) {
        this.userId = userId;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.messageId = messageId;
        this.messageText = messageText;
        this.messageUserFromId = messageUserFromId;
        this.messageUserToId = messageUserToId;
        this.messageDateSent = messageDateSent;
        this.messageDateRead = messageDateRead;
    }

    public ConversationInfo() {
    }
}
