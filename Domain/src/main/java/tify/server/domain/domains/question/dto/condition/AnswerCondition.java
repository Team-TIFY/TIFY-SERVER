package tify.server.domain.domains.question.dto.condition;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerCondition {

    private Long questionId;
    private List<Long> userIdList;
}
