package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class NeighborApplicationStatusNotWaitException extends BaseException {

    public static final BaseException EXCEPTION = new NeighborApplicationStatusNotWaitException();

    private NeighborApplicationStatusNotWaitException() {
        super(UserException.NEIGHBOR_APPLICATION_STATUS_NOT_WAIT_ERROR);
    }
}
