package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;

import java.util.Map;

public class CanceledStatusValidator extends AbstractChainValidator<Map<String, Object>> {

    @Override
    protected void checkParam(Map<String, Object> paramsMap) throws BadRequestException {

        if(paramsMap.get("status") == RelationshipStatus.CANCELED) {
            if(paramsMap.get("currentRelationship") == null)
                throw new BadRequestException("CANCELED Request can not be processed because there is no active relationship");
        }
    }
}
