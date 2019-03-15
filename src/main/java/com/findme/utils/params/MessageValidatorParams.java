package com.findme.utils.params;

import com.findme.model.Message;
import com.findme.model.Relationship;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class MessageValidatorParams {

    private Message message;
    private Relationship relationship;


}
