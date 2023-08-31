package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class AlreadyExistNeighborRelationshipException extends BaseException {

    public static final BaseException EXCEPTION = new AlreadyExistNeighborRelationshipException();

    private AlreadyExistNeighborRelationshipException() {
        super(UserException.ALREADY_EXIST_NEIGHBOR_RELATIONSHIP_ERROR);
    }
}
