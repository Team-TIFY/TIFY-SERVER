package tify.server.api.auth.service;

import static tify.server.core.consts.StaticVal.BEARER;

import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.domain.domains.user.adaptor.RefreshTokenAdaptor;

@UseCase
@RequiredArgsConstructor
public class LogoutUseCase {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final RefreshTokenAdaptor refreshTokenAdaptor;

    public void execute(String token) {
        String parseToken = validateAndParseToken(token);
        Long leftAccessTokenTTlSecond = jwtTokenProvider.getLeftAccessTokenTTLSecond(parseToken);

        // accessToken block
        redisTemplate
                .opsForValue()
                .set(parseToken, "logout", leftAccessTokenTTlSecond, TimeUnit.MILLISECONDS);

        refreshTokenAdaptor.delete(SecurityUtils.getCurrentUserId());
    }

    private String validateAndParseToken(String token) {
        return (token != null && token.startsWith(BEARER))
                ? token.substring(BEARER.length())
                : null;
    }
}
