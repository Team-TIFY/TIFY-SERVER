package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor 
public enum Tag {
    EXERCISE("hobby", "운동");

    final String category;
    final String value;
}
