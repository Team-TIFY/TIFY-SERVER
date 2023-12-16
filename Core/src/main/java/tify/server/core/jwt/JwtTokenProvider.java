package tify.server.core.jwt;

import static tify.server.core.consts.StaticVal.*;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tify.server.core.dto.AccessTokenDetail;
import tify.server.core.exception.ExpiredRefreshTokenException;
import tify.server.core.exception.ExpiredTokenException;
import tify.server.core.exception.InvalidTokenException;
import tify.server.core.properties.JwtProperties;
import tify.server.core.properties.OauthProperties;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final OauthProperties oauthProperties;

    @Value("${oauth2.apple.key-path}")
    private String appleKeyPath;

    ConcurrentHashMap<String, String> hashMap = new ConcurrentHashMap<>();

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw ExpiredTokenException.EXCEPTION;
        } catch (Exception e) {
            throw InvalidTokenException.EXCEPTION;
        }
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    private String buildAccessToken(
            Long id, Date issuedAt, Date accessTokenExpiresIn, String role) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, ACCESS_TOKEN)
                .claim("role", role)
                .setExpiration(accessTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    private String buildRefreshToken(Long id, Date issuedAt, Date refreshTokenExpiresIn) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(issuedAt)
                .setSubject(id.toString())
                .claim(TOKEN_TYPE, REFRESH_TOKEN)
                .setExpiration(refreshTokenExpiresIn)
                .signWith(encodedKey)
                .compact();
    }

    public String generateAccessToken(Long id, String role) {
        final Date issuedAt = new Date();
        final Date accessTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getAccessExp() * 1000);

        return buildAccessToken(id, issuedAt, accessTokenExpiresIn, role);
    }

    public String generateRefreshToken(Long id) {
        final Date issuedAt = new Date();
        final Date refreshTokenExpiresIn =
                new Date(issuedAt.getTime() + jwtProperties.getRefreshExp() * 1000);
        return buildRefreshToken(id, issuedAt, refreshTokenExpiresIn);
    }

    public boolean isAccessToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(ACCESS_TOKEN);
    }

    public boolean isRefreshToken(String token) {
        return getJws(token).getBody().get(TOKEN_TYPE).equals(REFRESH_TOKEN);
    }

    public AccessTokenDetail parseAccessToken(String token) {
        if (isAccessToken(token)) {
            Claims claims = getJws(token).getBody();
            return AccessTokenDetail.builder()
                    .userId(Long.parseLong(claims.getSubject()))
                    .role((String) claims.get("role"))
                    .build();
        }
        throw InvalidTokenException.EXCEPTION;
    }

    public Long parseRefreshToken(String token) {
        try {
            if (isRefreshToken(token)) {
                Claims claims = getJws(token).getBody();
                return Long.parseLong(claims.getSubject());
            }
        } catch (ExpiredTokenException e) {
            throw ExpiredRefreshTokenException.EXCEPTION;
        }
        throw InvalidTokenException.EXCEPTION;
    }

    public Long getRefreshTokenTTlSecond() {
        return jwtProperties.getRefreshExp();
    }

    public Long getAccessTokenTTlSecond() {
        return jwtProperties.getAccessExp();
    }

    public Long getLeftAccessTokenTTLSecond(String token) {
        return getJws(token).getBody().getExpiration().getTime();
    }

    public String buildAppleClientSecret()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        Date expirationDate =
                Date.from(
                        LocalDateTime.now()
                                .plusDays(30)
                                .atZone(ZoneId.systemDefault())
                                .toInstant());
        return Jwts.builder()
                .setHeaderParam("kid", oauthProperties.getAppleLogInKey())
                .setHeaderParam("alg", "ES256")
                .setIssuer(oauthProperties.getAppleTeamId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .setAudience("https://appleid.apple.com")
                .setSubject(oauthProperties.getAppleClientUrl())
                .signWith(SignatureAlgorithm.ES256, getPrivateKey())
                .compact();
    }

    public PrivateKey getPrivateKey()
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        String result = "";

        if (hashMap.get("apple") == null) {
            File file = new File(appleKeyPath);
            result = new String(Files.readAllBytes(file.toPath()));
            hashMap.put("apple", result);
        } else {
            result = hashMap.get("apple");
        }

        String key =
                result.replace("-----BEGIN PRIVATE KEY-----\n", "")
                        .replace("-----END PRIVATE KEY-----", "");

        byte[] encoded = Base64.decodeBase64(key.getBytes());

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        return keyFactory.generatePrivate(keySpec);
    }
}
