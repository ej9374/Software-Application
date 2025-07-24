package Software.SoftwareApplication.global.exception.custom;

public class RestClientException extends RuntimeException {
  public RestClientException() { super("Flask 통신 오류가 발생했습니다."); }
}
