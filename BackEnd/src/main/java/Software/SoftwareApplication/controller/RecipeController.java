package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 레시피 ID 리스트 db에서 찾기
    @PostMapping("/home")
    public ResponseEntity<List<HomeResponseDto>> getRecipesForHome(@RequestBody(required = false) List<Long> recipeIds) {
        System.out.println("Request received: " + recipeIds);
        if (recipeIds == null || recipeIds.isEmpty()) {
            recipeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        }
        List<HomeResponseDto> recipes = recipeService.getRecipesByIds(recipeIds);
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }

    // 캐글에서 받은 recipeId 리스트를 처리하여 레시피 반환
    @PostMapping("/list")
    public ResponseEntity<List<HomeResponseDto>> getRecipesFromIds(@RequestBody(required = false) List<Long> recipeIds) {
        System.out.println("Request received: " + recipeIds);
        if (recipeIds == null || recipeIds.isEmpty()) {
            recipeIds = List.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        }
        List<HomeResponseDto> recipes = recipeService.getRecipesByIds(recipeIds);
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }

    // 특정 recipe_id로 상세 정보 반환
    @GetMapping("/{recipeId}")
    public ResponseEntity<DetailedRecipeResponseDto> getRecipeDetails(@PathVariable Long recipeId) {
        DetailedRecipeResponseDto recipe = recipeService.getRecipeDetails(recipeId);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }



    // 랜덤으로 10개의 레시피 반환 (추가 필드 포함)
    @GetMapping("/random")
    public ResponseEntity<List<SignUpRecipeResponseDto>> getRandomRecipes() {
        List<SignUpRecipeResponseDto> recipes = recipeService.getRandomRecipesForSignUp();
        return ResponseEntity.status(HttpStatus.OK).body(recipes);
    }
}
