package Software.SoftwareApplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequestDto request) {
        // 클라이언트에서 전송한 회원가입 데이터를 처리하는 메서드 호출
        userService.registerUser(request);
        return ResponseEntity.ok("회원가입 성공");
    }
}
