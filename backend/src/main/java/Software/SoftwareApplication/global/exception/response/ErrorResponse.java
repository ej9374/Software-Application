package Software.SoftwareApplication.global.exception.response;

import Software.SoftwareApplication.global.exception.base.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status; //http 상태코드
    private String errorCode; //enum name
    private String message; // 보여줄 메세지

    public static ErrorResponse from(ErrorCode code){
        return new ErrorResponse(
                code.getStatus().value(),
                code.name(),
                code.getMessage()
        );
    }
}
