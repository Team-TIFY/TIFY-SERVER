package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class SelfNeighborException extends BaseException {

    public static final BaseException EXCEPTION = new SelfNeighborException();

    private SelfNeighborException() {
        super(UserException.SELF_NEIGHBOR_ERROR);
    }
}
