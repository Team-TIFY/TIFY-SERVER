package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class NeighborApplicationNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new NeighborApplicationNotFoundException();

    private NeighborApplicationNotFoundException() {
        super(UserException.NEIGHBOR_APPLICATION_NOT_FOUND_ERROR);
    }
}
