package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;

import java.util.Map;

public class DeletedStatusValidator extends AbstractChainValidator<Map<String, Object>> {

    @Override
    protected void checkParam(Map<String, Object> paramsMap) throws BadRequestException {

        if(paramsMap.get("status") == RelationshipStatus.DELETED) {
            System.out.println("deleted "+paramsMap.get("uerFromId")+"-"+paramsMap.get("userToId")+"-"+paramsMap.get("pipa"));

        }
    }
}
