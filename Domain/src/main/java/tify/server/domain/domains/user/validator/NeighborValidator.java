package tify.server.domain.domains.user.validator;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.exception.NeighborApplicationNotMatchedToUserIdException;

@Validator
@RequiredArgsConstructor
public class NeighborValidator {

    private final NeighborAdaptor neighborAdaptor;

    public void userIdAndNeighborApplicationValidate(
            NeighborApplication neighborApplication, Long toUserId) {
        if (!neighborApplication.getToUserId().equals(toUserId)) {
            throw NeighborApplicationNotMatchedToUserIdException.EXCEPTION;
        }
    }
}
