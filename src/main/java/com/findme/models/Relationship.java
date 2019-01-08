package com.findme.models;

import javax.persistence.*;

@Entity
@Table(name = "RELATIONSHIP")
public class Relationship extends GeneralModel{
    private Long id;
    private User userFrom;
    private User userTo;
    private RelationshipStatus status;

    @Id
    @SequenceGenerator(name = "RELATIONSHIP_SEQ", sequenceName = "RELATIONSHIP_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RELATIONSHIP_SEQ")
    @Column(name = "RELATIONSHIP_ID")
    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "USER_FROM_ID")
    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    @Column(name = "USER_TO_ID")
    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    @Column(name = "STATUS")
    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }
}
