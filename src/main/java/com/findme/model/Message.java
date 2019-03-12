package com.findme.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
@Getter @Setter @ToString
@EqualsAndHashCode(callSuper = true)
public class Message extends GeneralModel{

    @ManyToOne
    @JoinColumn(name="USER_FROM_ID", nullable = false)
    private User userFrom;

    @ManyToOne
    @JoinColumn(name="USER_TO_ID", nullable = false)
    private User userTo;

    @Column(name = "DATE_SENT")
    private Date dateSent;

    @Column(name = "DATE_EDITED")
    private Date dateEdited;

    @Column(name = "DATE_DELETED")
    private Date dateDeleted;

    @Column(name = "DATE_READ")
    private Date dateRead;

    @Column(name = "TEXT")
    private String text;

}
