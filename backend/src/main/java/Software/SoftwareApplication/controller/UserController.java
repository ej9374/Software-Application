package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 회원가입 처리
     */
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequestDto request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 오류 발생: " + e.getMessage());
        }
    }

    /**
     * 로그인 처리 (JWT 토큰 발급)
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Map<String, String> loginRequest) {
        try {
            String userId = loginRequest.get("id");

            // ID로 사용자 검증
            int user = userService.validateUser(userId);

            // JWT 토큰 생성
            String token = userService.generateToken(user);
            return ResponseEntity.ok(Map.of("token", token, "userId", user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "로그인 실패: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "서버 오류: " + e.getMessage()));
        }
    }

    /**
     * JWT 토큰 유효성 검증
     */
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
        try {
            boolean isValid = userService.validateToken(token.replace("Bearer ", ""));
            return isValid ? ResponseEntity.ok("유효한 토큰") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("토큰 검증 중 오류 발생: " + e.getMessage());
        }
    }
}
