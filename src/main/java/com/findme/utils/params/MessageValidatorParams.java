package com.findme.utils.params;

import com.findme.types.RelationshipStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
@Builder
public class MessageValidatorParams {

    private String text;
    private RelationshipStatus relationshipStatus;
    private Date dateSent;


}
