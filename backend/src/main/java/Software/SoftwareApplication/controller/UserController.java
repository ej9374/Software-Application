package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.entity.SignUpRecipeEntity;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.global.response.SuccessResponse;
import Software.SoftwareApplication.service.RecipeService;
import Software.SoftwareApplication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final RecipeService recipeService;


    // recipe 10개 추출해서 보내기
    @GetMapping("/random")
    public ResponseEntity<SuccessResponse<List<SignUpRecipeResponseDto>>> sendSignUpRecipes(){
        return SuccessResponse.onSuccess("recipe가 성공적으로 추출되었습니다.", HttpStatus.OK, recipeService.getRandomRecipesForSignUp());
    }

    @PostMapping("/signup")
    public ResponseEntity<SuccessResponse<SignUpRequestDto>> signUp(@RequestBody SignUpRequestDto request) {
  /*      try {
            userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("입력 오류: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 중 오류 발생: " + e.getMessage());
        }*/
        userService.signUp(request);
        return SuccessResponse.onSuccess("회원가입이 성공적으로 완료되었습니다.", HttpStatus.CREATED, request);
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/signIn")
    public ResponseEntity<SuccessResponse<Map<String, String>>> signIn(@RequestParam String id, @RequestParam String password){

        Map<String, String> token = userService.login(id, password);
        return SuccessResponse.onSuccess("성공적으로 로그인하였습니다.", HttpStatus.OK, token);
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Void>> logout() {
        return SuccessResponse.ok("성공적으로 로그아웃하였습니다.");
    }

    /**
     * JWT 토큰 유효성 검증
     */
//    @GetMapping("/validate")
//    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token) {
//        try {
//            boolean isValid = userService.validateToken(token.replace("Bearer ", ""));
//            return isValid ? ResponseEntity.ok("유효한 토큰") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 토큰");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("토큰 검증 중 오류 발생: " + e.getMessage());
//        }
//    }
}
