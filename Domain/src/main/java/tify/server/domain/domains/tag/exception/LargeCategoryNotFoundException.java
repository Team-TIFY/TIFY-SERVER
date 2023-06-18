package tify.server.domain.domains.tag.exception;


import tify.server.core.exception.BaseException;

public class LargeCategoryNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new LargeCategoryNotFoundException();

    private LargeCategoryNotFoundException() {
        super(TagException.LARGECATEGORY_NOT_FOUND_ERROR);
    }
}
