package tify.server.domain.domains.question.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    BEVERAGE("음료");

    final String value;
}
