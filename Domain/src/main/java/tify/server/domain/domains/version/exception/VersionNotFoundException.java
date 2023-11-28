package tify.server.domain.domains.version.exception;


import tify.server.core.exception.BaseException;

public class VersionNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new VersionNotFoundException();

    private VersionNotFoundException() {
        super(VersionException.VERSION_NOT_FOUND_ERROR);
    }
}
