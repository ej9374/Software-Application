package Software.SoftwareApplication.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
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

    // 이거 왜하는거지 일치하는가 보는건가? 로그인한 유저 누구인가?
    public String extractId(String jws) {
        Jws<Claims> claims = Jwts.parser().verifyWith(accessTokenkey).build()
                .parseClaimsJws(jws); // parse 파싱을 통해 검증

        return claims.getPayload().getSubject(); // subject id 리턴 할 수 있음
    }

    public boolean validateToken(String jws, String id) {
        return id.equals(extractId(jws)) && !isTokenExpired(jws, TokenType.ACCESS_TOKEN);
    }

    public boolean isAccessTokenExpired(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(accessTokenkey).build()
                .parseClaimsJws(token);
        return claims.getPayload().getExpiration().before(new Date());
    }

    public boolean isRefreshTokenExpired(String token) {
        Jws<Claims> claims = Jwts.parser().verifyWith(refreshTokenKey).build()
                .parseClaimsJws(token);
        return claims.getPayload().getExpiration().before(new Date());
    }

    public Date getExpired(String refreshToken) {
        return Jwts.parser().verifyWith(refreshTokenKey).build()
                .parseSignedClaims(refreshToken)
                .getPayload().getExpiration();
    }
    // 만료시 ExpiredJwtException 던짐
}
