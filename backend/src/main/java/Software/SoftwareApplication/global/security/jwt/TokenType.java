package Software.SoftwareApplication.global.security.jwt;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;


public enum TokenType {
    ACCESS_TOKEN(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusHours(1).toInstant())),
    REFRESH_TOKEN(Date.from(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).plusDays(7).toInstant()));

    private final Date expirationTime;
    TokenType(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }
}
