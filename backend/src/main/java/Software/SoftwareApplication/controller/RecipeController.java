package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.RecipeResponseDto;
import Software.SoftwareApplication.global.response.SuccessResponse;
import Software.SoftwareApplication.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/collaborative")
    public ResponseEntity<SuccessResponse<List<RecipeResponseDto>>> getHomeRecipes(@RequestBody String userId) {
        List<RecipeResponseDto> recipes = recipeService.getCollaborativeRecommendation(userId);
        return SuccessResponse.onSuccess("성공적으로 홈화면 레시피를 띄웠습니다.", HttpStatus.OK, recipes);
    }

    @PostMapping("/content-based")
    public ResponseEntity<SuccessResponse<List<RecipeResponseDto>>> getContentBasedRecommendationRecipes(@RequestBody Long recipeId) {
        List<RecipeResponseDto> recommendations = recipeService.getContentBasedRecommendation(recipeId);
        return SuccessResponse.onSuccess("성공적으로 content-based 추천 레시피를 찾았습니다.", HttpStatus.OK, recommendations);
    }

    // 특정 recipe_id로 상세 정보 반환
    @GetMapping("/{recipeId}")
    public ResponseEntity<SuccessResponse<DetailedRecipeResponseDto>> getRecipeDetails(@PathVariable Long recipeId) {
        DetailedRecipeResponseDto recipe = recipeService.getRecipeDetails(recipeId);
        return SuccessResponse.onSuccess("해당 레시피 아이디에 대한 레시피 정보를 찾았습니다.",HttpStatus.OK, recipe);
    }




}
