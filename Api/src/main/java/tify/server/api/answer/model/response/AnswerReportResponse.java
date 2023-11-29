package tify.server.api.answer.model.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AnswerReportResponse {

    private final Long userId;

    private final Long answerId;

    private final boolean reportSuccess;
}
