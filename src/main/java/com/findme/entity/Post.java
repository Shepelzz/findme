package com.findme.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "POST")
@Getter @Setter @ToString
@EqualsAndHashCode(callSuper = true)

public class Post extends GeneralModel {

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "DATE_POSTED")
    private Date datePosted;

    @Column(name = "LOCATION")
    private String location;

    @ManyToOne
    @JoinColumn(name="USER_POSTED_ID", nullable = false)
    private User userPosted;

    @ManyToOne
    @JoinColumn(name="USER_PAGE_POSTED_ID", nullable = false)
    private User userPagePosted;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="USERS_TAGGED", joinColumns = @JoinColumn(name = "POST_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> usersTagged;


    public String getTaggedUsersAndLocation(){
        StringBuilder result = new StringBuilder();
        if(usersTagged != null && usersTagged.size() > 0){
            result.append("with ");
            for(int i = 0; i < usersTagged.size(); i++){
                result.append(usersTagged.get(i).getFirstName())
                        .append(" ")
                        .append(usersTagged.get(i).getLastName().substring(0, 1))
                        .append(".");
                if(i != usersTagged.size()-1)
                    result.append(usersTagged.size() == 2 ? " and " : ", ");
            }
        }
        if(location != null && !location.equals(""))
            result.append(" in ").append(location);
        return result.toString();
    }

    public String getDatePostedString() {
        return  new SimpleDateFormat("dd.MM.yyyy HH:mm").format(datePosted);
    }

    //TODO
    //levels permissions

    //TODO
    //comments


}
