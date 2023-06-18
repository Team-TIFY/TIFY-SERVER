package tify.server.domain.domains.tag.exception;


import tify.server.core.exception.BaseException;

public class SmallCategoryNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new SmallCategoryNotFoundException();

    private SmallCategoryNotFoundException() {
        super(TagException.SMALLCATEGORY_NOT_FOUND_ERROR);
    }
}
