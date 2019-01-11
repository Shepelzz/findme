package com.findme.models;

import com.findme.types.RelationshipStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "RELATIONSHIP")
@IdClass(value = Relationship.RelationshipId.class)
public class Relationship implements Serializable {

    private User userFrom;
    private User userTo;
    private RelationshipStatus status;

    @Id
    @ManyToOne
    @JoinColumn(name="USER_FROM_ID", nullable = false)
    public User getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    @Id
    @ManyToOne
    @JoinColumn(name="USER_TO_ID", nullable = false)
    public User getUserTo() {
        return userTo;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    public RelationshipStatus getStatus() {
        return status;
    }

    public void setStatus(RelationshipStatus status) {
        this.status = status;
    }


    static class RelationshipId implements Serializable{
        private User userFrom;
        private User userTo;

        public User getUserFrom() {
            return userFrom;
        }

        public void setUserFrom(User userFrom) {
            this.userFrom = userFrom;
        }

        public User getUserTo() {
            return userTo;
        }

        public void setUserTo(User userTo) {
            this.userTo = userTo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RelationshipId that = (RelationshipId) o;
            return Objects.equals(userFrom, that.userFrom) &&
                    Objects.equals(userTo, that.userTo);
        }

        @Override
        public int hashCode() {

            return Objects.hash(userFrom, userTo);
        }
    }
}
