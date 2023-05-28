package tify.server.core.exception;


import tify.server.core.dto.ErrorDetail;

public interface BaseErrorCode {

    public ErrorDetail getErrorDetail();

    //    String getExplainError() throws NoSuchFieldException;
}
