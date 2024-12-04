package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.api.FlaskApiClient;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class HomeController {

    // 클래스 시작 부분에 Logger 추가
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private RecipeService recipeService; // 서비스 클래스 주입

    @Autowired
    private FlaskApiClient flaskApiClient; // Flask API 클라이언트 주입

    @PostMapping("/home")
    public ResponseEntity<List<HomeResponseDto>> getHomeRecipes(@RequestBody Long userId) {
        // Flask 서버에서 추천 ID 가져오기
        List<Integer> recommendedIds = flaskApiClient.getRecommendations(userId);

        // Integer -> Long 변환
        List<Long> recipeIds = new ArrayList<>();
        try {
            // Flask 서버에서 추천 ID 가져오기
            recommendedIds = flaskApiClient.getRecommendations(userId);

            // Integer -> Long 변환
            for (Integer id : recommendedIds) {
                recipeIds.add(id.longValue());
            }
        } catch (Exception e) {
            // Flask 호출 실패 시 에러 로그 추가
            logger.error("Failed to fetch recommendations from Flask server for userId: {}", userId, e);
        }

        // 추천 결과가 없을 경우 기본값 설정
        if (recipeIds.isEmpty()) {
            recipeIds = List.of(137739L, 31490L, 112140L, 59389L, 44061L, 5289L, 25274L, 67888L, 70971L, 75452L);
            // 로그 추가
            logger.warn("No recommendations found for userId: {}. Using default recommendations.", userId);
        }

        // RecipeService를 사용하여 레시피 데이터를 가져옴
        List<HomeResponseDto> recipes = recipeService.getRecipesByIds(recipeIds);

        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }
}
