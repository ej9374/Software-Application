package Software.SoftwareApplication.global.exception.base;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 공통
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "입력값이 유효하지 않습니다."),

    // 사용자 관련
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 유효하지 않습니다."),
    NULL_RATING_VALUE(HttpStatus.BAD_REQUEST, "레시피 ID 또는 평점이 비어 있습니다."),

    // 레시피 관련
    RECIPE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 레시피를 찾을 수 없습니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
