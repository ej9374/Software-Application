package Software.SoftwareApplication.controller;

import Software.SoftwareApplication.api.FlaskApiClient;
import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping("/list")
    public ResponseEntity<List<HomeResponseDto>> getSimilarRecipes(@RequestBody Map<String, Long> request) {
        Long recipeId = request.get("recipeId");
        if (recipeId == null) {
            System.out.println("Error: No recipeId provided in the request.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(List.of());
        }

        System.out.println("Received recipeId: " + recipeId);

        List<HomeResponseDto> similarRecipes = recipeService.getRecommendedRecipes(recipeId);

        System.out.println("Found recommended recipes: " + similarRecipes);
        return ResponseEntity.status(HttpStatus.OK).body(similarRecipes);
    }



    // 특정 recipe_id로 상세 정보 반환
    @GetMapping("/{recipeId}")
    public ResponseEntity<DetailedRecipeResponseDto> getRecipeDetails(@PathVariable Long recipeId) {
        DetailedRecipeResponseDto recipe = recipeService.getRecipeDetails(recipeId);
        return ResponseEntity.status(HttpStatus.OK).body(recipe);
    }




}
