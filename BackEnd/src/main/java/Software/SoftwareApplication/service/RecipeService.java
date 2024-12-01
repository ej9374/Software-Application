package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 캐글에서 전달받은 레시피 ID 리스트로 이름과 시간 반환
    public List<HomeResponseDto> getRecipesByIds(List<Long> recipeIds) {
        List<RecipeEntity> recipes = recipeRepository.findAllById(recipeIds);

        return recipes.stream()
                .map(recipe -> new HomeResponseDto(
                        recipe.getRecipeId(),
                        recipe.getName(),
                        recipe.getMinutes()
                ))
                .collect(Collectors.toList());
    }

    // 특정 recipe_id로 상세 정보 반환
    public DetailedRecipeResponseDto getRecipeDetails(Long recipeId) {
        RecipeEntity recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new IllegalArgumentException("해당 레시피를 찾을 수 없습니다. ID: " + recipeId));

        return new DetailedRecipeResponseDto(
                recipe.getRecipeId(),
                recipe.getName(),
                recipe.getMinutes(),
                recipe.getTags(),
                recipe.getNutrition(),
                recipe.getNSteps(),
                recipe.getSteps(),
                recipe.getDescription(),
                recipe.getIngredients(),
                recipe.getNIngredients()
        );
    }

    // 랜덤으로 10개의 레시피 반환 (SignUpRecipeResponseDto 활용)
    public List<SignUpRecipeResponseDto> getRandomRecipesForSignUp() {
        List<RecipeEntity> allRecipes = recipeRepository.findAll();
        Random random = new Random();

        return random.ints(0, allRecipes.size())
                .distinct()
                .limit(10)
                .mapToObj(allRecipes::get)
                .map(recipe -> new SignUpRecipeResponseDto(
                        recipe.getRecipeId(),
                        recipe.getName(),
                        recipe.getMinutes(),
                        recipe.getTags(),
                        recipe.getNSteps(),
                        recipe.getDescription(),
                        recipe.getIngredients()
                ))
                .collect(Collectors.toList());
    }
}
