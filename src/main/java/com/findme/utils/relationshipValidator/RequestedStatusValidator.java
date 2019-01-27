package com.findme.utils.relationshipValidator;

import com.findme.exception.BadRequestException;
import com.findme.models.Relationship;
import com.findme.types.RelationshipStatus;
import com.findme.utils.AbstractChainValidator;

import java.util.Map;

public class RequestedStatusValidator extends AbstractChainValidator<Map<String, Object>> {

    @Override
    protected void checkParam(Map<String, Object> paramsMap) throws BadRequestException {
        //запрос - добавить в друзья
        if(paramsMap.get("status") == RelationshipStatus.REQUESTED) {
            System.out.println("requested processed");
            Relationship rel = (Relationship) paramsMap.get("currentRelationship");

            //Связи не было ранее - создаем REQUESTED
            if(rel == null ){
                //relationshipDAO.saveRelationship(userFromIdL, userToIdL, RelationshipStatus.REQUESTED);
            }
            //Есть запрос на добавление от друга к нам - обновляем на FRIENDS
            else if (rel.getStatus() == RelationshipStatus.REQUESTED){
                //relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.FRIENDS);
            }
            //Есть запрос отмененный либо связь удалена - обновляем на REQUESTED
            else if (rel.getStatus() == RelationshipStatus.REJECTED || rel.getStatus() == RelationshipStatus.DELETED || rel.getStatus() == RelationshipStatus.CANCELED){
                //relationshipDAO.updateRelationship(rel.getUserFrom().getId(), rel.getUserTo().getId(), userFromIdL, userToIdL, RelationshipStatus.REQUESTED);
            }
        }
    }
}
