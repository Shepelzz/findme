package com.findme.utils.params;

import com.findme.types.RelationshipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class RelationshipValidatorParams {

    private String userFromId;
    private String userToId;
    private RelationshipStatus oldStatus;
    private RelationshipStatus newStatus;
    private Date relationshipDateModified;
    private int friendsCnt;
    private int outgoingReqCnt;

}
