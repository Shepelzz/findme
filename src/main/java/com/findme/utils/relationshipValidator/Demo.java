package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;

import java.util.HashMap;
import java.util.Map;

public class Demo {
    public static void main(String[] args) throws BadRequestException {

        AbstractChainValidator<Map<String, Object>> requestedVal = new RequestedStatusValidator();
        AbstractChainValidator<Map<String, Object>> deletedVal = new DeletedStatusValidator();
        AbstractChainValidator<Map<String, Object>> canceledVal = new CanceledStatusValidator();
        AbstractChainValidator<Map<String, Object>> rejectedVal = new RejectedStatusValidator();
        AbstractChainValidator<Map<String, Object>> friendsVal = new FriendsStatusValidator();

        requestedVal.setNextAbstractChainValidator(friendsVal);
        friendsVal.setNextAbstractChainValidator(canceledVal);
        canceledVal.setNextAbstractChainValidator(deletedVal);
        deletedVal.setNextAbstractChainValidator(rejectedVal);

        Map<String, Object> params = new HashMap<>();
        params.put("status", RelationshipStatus.valueOf("FRIENDS"));
        params.put("userFromId", 23L);
        params.put("userToId", 4L);


        requestedVal.check(params);

    }
}
