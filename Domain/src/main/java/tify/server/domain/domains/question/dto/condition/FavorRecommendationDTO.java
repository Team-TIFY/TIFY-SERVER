package tify.server.domain.domains.question.dto.condition;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.FavorAnswer;

@Getter
@Builder
@AllArgsConstructor
public class FavorRecommendationDTO {

    private final Long questionNumber;

    private final String answer;

    public static FavorRecommendationDTO from(FavorAnswer favorAnswer) {
        return FavorRecommendationDTO.builder()
                .questionNumber(favorAnswer.getFavorQuestion().getNumber())
                .answer(favorAnswer.getAnswerContent())
                .build();
    }
}
