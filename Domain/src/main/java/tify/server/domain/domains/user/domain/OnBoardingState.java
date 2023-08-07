package tify.server.domain.domains.user.domain;


import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.exception.UserException;

@Getter
@AllArgsConstructor
public enum OnBoardingState {

    // Todo: 노션 보고 추가하기
    DURING_EXERCISE("운동 중 \uD83D\uDCAA"),
    DURING_CLIMBING("클라이밍 중 \uD83E\uDDD7\u200D♂️"),
    DELAY_PT("PT 미루는 중 \uD83C\uDFCB️\u200D♂️\uD83E\uDD38\u200D♂️"),
    EXERCISE_IN_GYM("헬스장에서 운동 중 \uD83C\uDFCB️\u200D♂️");

    final String value;

    public static OnBoardingState toEnum(String value) {
        return Stream.of(OnBoardingState.values())
                .filter(onBoardingState -> onBoardingState.value.equals(value))
                .findFirst()
                .orElseThrow(
                        () -> new BaseException(UserException.ON_BOARDING_STATE_NOT_FOUND_ERROR));
    }
}
