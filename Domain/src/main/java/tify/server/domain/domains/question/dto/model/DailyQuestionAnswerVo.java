package tify.server.domain.domains.question.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.domain.DailyQuestion;

@Getter
@AllArgsConstructor
public class DailyQuestionAnswerVo {

    private int month;

    private DailyQuestion dailyQuestion;

    private Answer answer;
}
