package tify.server.domain.domains.question.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DailyQuestionCategory {
    FOOD("음식"),
    MUSIC("음악"),
    RELATIONSHIP("관계"),
    BEVERAGE("음료"),
    CULTURE_LIFE("문화셍활"),
    TRIP("여행"),
    ME("나"),
    ;

    final String value;
}
