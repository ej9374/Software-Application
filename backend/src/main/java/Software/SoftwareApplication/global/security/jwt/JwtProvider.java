package Software.SoftwareApplication.global.security.jwt;

import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.global.exception.base.CustomException;
import Software.SoftwareApplication.global.exception.base.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String accessTokenKeyString; // 자유적으로 어렵게 설정

    @Value("${jwt.refresh-secret}")
    private String refreshTokenKeyString;

    SecretKey accessTokenkey = Keys.hmacShaKeyFor(accessTokenKeyString.getBytes(StandardCharsets.UTF_8));
    SecretKey refreshTokenKey = Keys.hmacShaKeyFor(refreshTokenKeyString.getBytes(StandardCharsets.UTF_8));
    // 암호화 방식에 따라 헤더 설정

    public String createAccessToken(String id) {
        return createToken(id, TokenType.ACCESS_TOKEN);
    }

    public String createRefreshToken(String id) {
        return createToken(id, TokenType.REFRESH_TOKEN);
    }

    private String createToken(String id, TokenType tokenType) {
        String token = Jwts.builder()
                .subject(id) // 일반적으로 id 설정
//                .claim()  // key, value 형태로 세팅해줘야됨 (role 정보 포함)
                .expiration(tokenType.getExpirationTime()) // 만료시간
                .issuedAt(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toInstant()))
                .signWith((tokenType == TokenType.ACCESS_TOKEN) ? accessTokenkey : refreshTokenKey)
                .compact(); // header payload 합쳐서 signature 만드는 것
        return token;
    }

    public boolean validationAccessToken(String token) {
        return validateTokenInternal(token, accessTokenkey);
    }

    public boolean validationRefreshToken(String token) {
        return validateTokenInternal(token, refreshTokenKey);
    }

    public boolean validateTokenInternal(String token, SecretKey key){
        try {
            Jwts.parser().verifyWith(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JWT_TOKEN_EXPIRED);
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.JWT_TOKEN_INVALID);
        }
    }

    public String extractId(String jws) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(accessTokenkey)
                .build()
                .parseClaimsJws(jws); // parse 파싱을 통해 검증
        return claims.getPayload().getSubject(); // subject id 리턴 할 수 있음
    }

    public Claims getClaimsFromAccessToken(String token) {
        return getClaimsInternal(token, accessTokenkey);
    }

    public Claims getClaimsFromRefreshToken(String token) {
        return getClaimsInternal(token, refreshTokenKey);
    }

    public Claims getClaimsInternal(String token, SecretKey key) {
        try{
            return Jwts.parser().verifyWith(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.JWT_TOKEN_EXPIRED);
        }
    }
    public boolean isTokenExpired(Date expirationDate) {
        return expirationDate.before(new Date());
    }
}
