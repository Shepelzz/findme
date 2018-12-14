package com.findme.models;

import java.util.Date;

public class Message {
    private Long id;
    private String text;
    private Date dateSent;
    private Date dateRead;
    private User userFrom;
    private User userTo;
}
