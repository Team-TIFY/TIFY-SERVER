package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LargeCategory {
    BEAUTY("뷰티"),
    FASHION("패션"),
    HOBBY("취미"),
    ;

    final String value;
}
