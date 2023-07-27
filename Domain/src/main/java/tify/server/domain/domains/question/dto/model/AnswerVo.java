package tify.server.domain.domains.question.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.user.domain.User;

@Getter
@AllArgsConstructor
public class AnswerVo {

    private final Answer answer;

    private final User user;
}
