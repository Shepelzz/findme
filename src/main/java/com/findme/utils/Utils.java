package com.findme.utils;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import lombok.extern.log4j.Log4j;

@Log4j
public final class Utils {

    public static Long castStringToLong(String param) throws BadRequestException {
        checkNull(param);
        try{
            return Long.valueOf(param);
        } catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    public static RelationshipStatus castStringToRelationshipStatus(String param) throws BadRequestException {
        checkNull(param);
        try{
            return RelationshipStatus.valueOf(param);
        } catch (IllegalArgumentException e){
            log.warn(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    public static void checkNull(Object param) throws BadRequestException {
        if(param == null)
            throw new BadRequestException("Parameter can not be null");
    }



}
