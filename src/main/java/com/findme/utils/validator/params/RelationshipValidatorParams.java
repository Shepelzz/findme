package com.findme.utils.validator.params;

import com.findme.types.RelationshipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class RelationshipValidatorParams {

    private RelationshipStatus oldStatus;
    private RelationshipStatus newStatus;
    private Date relationshipDateModified;
    private int friendsCnt;
    private int outgoingReqCnt;

}
