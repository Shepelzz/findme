package com.findme;

import com.findme.exception.BadRequestException;
import com.findme.types.RelationshipStatus;
import com.findme.utils.relationshipValidator.*;

public class Demo {
    public static void main(String[] args) throws BadRequestException {

        RelationshipStatus oldStatus = RelationshipStatus.REJECTED;
        RelationshipStatus newStatus = RelationshipStatus.REJECTED;

        AbstractRelationshipValidator friendsVal = new FriendsStatusValidator();
        AbstractRelationshipValidator canceledVal = new CanceledStatusValidator();
        AbstractRelationshipValidator deletedVal = new DeletedStatusValidator();
        AbstractRelationshipValidator rejectedVal = new RejectedStatusValidator();
        AbstractRelationshipValidator requestedVal = new RequestedStatusValidator();

        friendsVal.setNextAbstractChainValidator(canceledVal);
        canceledVal.setNextAbstractChainValidator(deletedVal);
        deletedVal.setNextAbstractChainValidator(rejectedVal);
        rejectedVal.setNextAbstractChainValidator(requestedVal);

        friendsVal.check(oldStatus, newStatus);
        System.out.println("Ok");

    }

}
