package tify.server.domain.domains.question.dto.condition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class AnswerCondition {

    private Long questionId;
    private Pageable pageable;
}
