package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.entity.RecommendationsEntity;
import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.entity.SignUpRecipeEntity;
import Software.SoftwareApplication.repository.RecommendationsRepository;
import Software.SoftwareApplication.repository.RecipeRepository;
import Software.SoftwareApplication.repository.SignUpRecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final SignUpRecipeRepository signUpRecipeRepository;

    /**
     * 특정 recipeId를 기반으로 추천 레시피 정보를 반환합니다.
     */
    public List<HomeResponseDto> getRecommendedRecipes(Long recipeId) {
        System.out.println("Fetching recommendations for recipeId: " + recipeId);

        RecommendationsEntity recommendations = recommendationsRepository.findById(recipeId)
                .orElseThrow(() -> {
                    System.out.println("No recommendations found for recipeId: " + recipeId);
                    return new IllegalArgumentException("추천 정보를 찾을 수 없습니다. Recipe ID: " + recipeId);
                });

        List<RecipeEntity> recommendedRecipes = new ArrayList<>();
        if (recommendations.getFirst() != null) {
            recommendedRecipes.add(recommendations.getFirst());
        }
        if (recommendations.getSecond() != null) {
            recommendedRecipes.add(recommendations.getSecond());
        }
        if (recommendations.getThird() != null) {
            recommendedRecipes.add(recommendations.getThird());
        }
        if (recommendations.getForth() != null) {
            recommendedRecipes.add(recommendations.getForth());
        }
        if (recommendations.getFifth() != null) {
            recommendedRecipes.
                    add(recommendations.getFifth());
        }

        System.out.println("Recommended recipes: " + recommendedRecipes);
        return recommendedRecipes.stream()
                .map(recipe -> new HomeResponseDto(recipe.getRecipeId(), recipe.getName(), recipe.getMinutes()))
                .toList();
    }



    /**
     * 특정 레시피 ID 리스트로 레시피 이름과 시간을 반환합니다.
     */
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

    /**
     * 특정 recipe_id를 기반으로 상세 레시피 정보를 반환합니다.
     */
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



    /**
     * 랜덤으로 10개의 레시피를 반환합니다. (회원가입 시 활용) ->  SignUpRecipeEntity에서 랜덤으로 10개뽑아서 띄워야됨!
     */
    public List<SignUpRecipeResponseDto> getRandomRecipesForSignUp() {
        List<RecipeEntity> allRecipes = signUpRecipeRepository.findAllIds(); // 모든 Recipe ID를 가져옵니다.

        //랜덤하게 섞고 그중 10개 추출
        Collections.shuffle(allRecipes);

        List<RecipeEntity> selected = allRecipes.subList(0, 10);

        return selected.stream()
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
