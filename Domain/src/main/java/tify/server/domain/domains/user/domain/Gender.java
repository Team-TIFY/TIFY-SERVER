package tify.server.domain.domains.user.domain;


import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.exception.UserGenderNotFoundException;

@Getter
@AllArgsConstructor
public enum Gender {
    MALE("남자"),
    FEMALE("여자");

    final String value;

    public static Gender toGender(String gender) {
        return Stream.of(Gender.values())
                .filter(genderType -> genderType.name().toLowerCase().equals(gender))
                .findFirst()
                .orElseThrow(() -> UserGenderNotFoundException.Exception);
    }
}
