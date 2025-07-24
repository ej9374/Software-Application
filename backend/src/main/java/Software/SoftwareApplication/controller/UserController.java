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
        userService.signUp(request);
        return SuccessResponse.onSuccess("회원가입이 성공적으로 완료되었습니다.", HttpStatus.CREATED, request);
    }

    /**
     * 로그인 처리
     */
    @PostMapping("/signin")
    public ResponseEntity<SuccessResponse<Map<String, String>>> signIn(@RequestParam String id, @RequestParam String password){

        Map<String, String> token = userService.login(id, password);
        return SuccessResponse.onSuccess("성공적으로 로그인하였습니다.", HttpStatus.OK, token);
    }

    /**
     * access token 만료시 처리
     */
    @PostMapping("/refresh")
    public ResponseEntity<SuccessResponse<Map<String, String>>> refresh(@RequestParam String refreshToken){
        Map<String, String> token = userService.refreshAccessToken(refreshToken);
        return SuccessResponse.onSuccess("성공적으로 access token을 재발급했습니다.", HttpStatus.OK, token);
    }
    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<Void>> logout(String id) {
        userService.logout(id);
        return SuccessResponse.ok("성공적으로 로그아웃하였습니다.");
    }
}
