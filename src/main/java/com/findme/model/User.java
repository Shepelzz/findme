package com.findme.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "USERS")
@Getter @Setter //@ToString
@EqualsAndHashCode(callSuper = true)
public class User extends GeneralModel{

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToOne
    @JoinColumn(name="COUNTRY_ID")
    private Country country;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AGE")
    private Integer age;

    @Column(name = "DATE_REGISTERED")
    private Date dateRegistered;

    @Column(name = "DATE_LAST_ACTIVE")
    private Date dateLastActive;

    //TODO enum
    @Column(name = "RELATIONSHIP_STATUS")
    private String relationshipStatus;

    @Column(name = "RELIGION")
    private String religion;

    //TODO from existed data
    @Column(name = "SCHOOL")
    private String school;

    @Column(name = "UNIVERSITY")
    private String university;

    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "userFrom")
    private List<Message> messagesSent;

    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    @OneToMany (cascade = CascadeType.ALL, mappedBy = "userTo")
    private List<Message> messagesReceived;

//    @Column(name = "PHOTO")
//    private BitSet photo;

//    private String[] interests;


    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
