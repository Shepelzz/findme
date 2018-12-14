package com.findme.models;

import java.util.Date;
import java.util.List;

public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    //TODO from existed data
    private String country;
    private String city;

    private Integer age;
    private Date dateregistered;
    private Date dateLastActive;
    //TODO enum
    private String relationshipStatus;
    private String religion;
    //TODO from existed data
    private String school;
    private String university;

    private List<Message> messagesSent;
    private List<Message> messagesReceived;

//    private String[] interests;




}
