package com.findme.utils.validator.params;

import com.findme.types.RelationshipStatus;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class RelationshipValidatorParams {

    private RelationshipStatus oldStatus;
    private RelationshipStatus newStatus;
    private Date relationshipDateModified;
    private int friendsCnt;
    private int outgoingReqCnt;

    public static ParamsBuilder newBuilder() {
        return new RelationshipValidatorParams().new ParamsBuilder();
    }

    public class ParamsBuilder {
        private ParamsBuilder(){}

        public ParamsBuilder setOldStatus(RelationshipStatus oldStatus) {
            RelationshipValidatorParams.this.oldStatus = oldStatus;
            return this;
        }

        public ParamsBuilder setNewStatus(RelationshipStatus newStatus) {
            RelationshipValidatorParams.this.newStatus = newStatus;
            return this;
        }

        public ParamsBuilder setRelationshipDateModified(Date relationshipDateModified) {
            RelationshipValidatorParams.this.relationshipDateModified = relationshipDateModified;
            return this;
        }

        public ParamsBuilder setFriendsCnt(int friendsCnt) {
            RelationshipValidatorParams.this.friendsCnt = friendsCnt;
            return this;
        }

        public ParamsBuilder setOutgoingReqCnt(int outgoingReqCnt) {
            RelationshipValidatorParams.this.outgoingReqCnt = outgoingReqCnt;
            return this;
        }

        public RelationshipValidatorParams build() {
            return RelationshipValidatorParams.this;
        }
    }
}
