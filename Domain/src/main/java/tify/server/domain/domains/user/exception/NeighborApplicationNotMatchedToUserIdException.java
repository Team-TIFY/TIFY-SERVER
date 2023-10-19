package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class NeighborApplicationNotMatchedToUserIdException extends BaseException {

    public static final BaseException EXCEPTION =
            new NeighborApplicationNotMatchedToUserIdException();

    private NeighborApplicationNotMatchedToUserIdException() {
        super(UserException.NEIGHBOR_APPLICATION_NOT_MATCHED_TO_USER_ID_ERROR);
    }
}
