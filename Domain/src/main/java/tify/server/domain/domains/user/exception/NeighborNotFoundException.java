package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class NeighborNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new NeighborNotFoundException();

    private NeighborNotFoundException() {
        super(UserException.NEIGHBOR_NOT_FOUND_ERROR);
    }
}
