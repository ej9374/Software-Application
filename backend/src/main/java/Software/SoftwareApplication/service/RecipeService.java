package Software.SoftwareApplication.service;

import Software.SoftwareApplication.global.exception.custom.RestClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import Software.SoftwareApplication.dto.DetailedRecipeResponseDto;
import Software.SoftwareApplication.dto.RecipeResponseDto;
import Software.SoftwareApplication.dto.SignUpRecipeResponseDto;
import Software.SoftwareApplication.entity.ExistingUserEntity;
import Software.SoftwareApplication.entity.RecommendationsEntity;
import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.entity.SignUpRecipeEntity;
import Software.SoftwareApplication.global.exception.base.CustomException;
import Software.SoftwareApplication.global.exception.base.ErrorCode;
import Software.SoftwareApplication.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecommendationsRepository recommendationsRepository;
    private final SignUpRecipeRepository signUpRecipeRepository;
    private final ExistingUserRepository existingUserRepository;
    private final MatchRecipeRepository matchRecipeRepository;
    private final RestTemplate restTemplate;

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public List<RecipeResponseDto> getCollaborativeRecommendation(String oldUserId) {

        ExistingUserEntity user = existingUserRepository.findByUserId(oldUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Integer userId = user.getNewUserId();

        Map<String, Object> body = new HashMap<>();
        body.put("userId", userId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        List<Integer> recommendIds = new ArrayList<>();

        try {
            ResponseEntity<List<Integer>> response = restTemplate.exchange(
                    flaskServerUrl,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Integer>>() {}
            );
            if (response.getBody() != null && response.getStatusCode() == HttpStatus.OK) {
                recommendIds = response.getBody();
            }
        } catch (RestClientException e) {
            log.error(e.getMessage());
        }

        List<Long> recipeIds = new ArrayList<>();

        for (Integer id : recommendIds) {
            Long recipeId = matchRecipeRepository.findByNewRecipeId(id).getRecipe().getRecipeId();
            recipeIds.add(recipeId);
        }

        // 추천 결과가 없을 경우 기본값 설정
        if (recommendIds.isEmpty()) {
            recipeIds = List.of(137739L, 31490L, 112140L, 59389L, 44061L, 5289L, 25274L, 67888L, 70971L, 75452L);
            log.info("No Collaborative recommendations found for userId: {}", userId);
        }

        return getRecipesByIds(recipeIds);
    }

    /**
     * 특정 recipeId를 기반으로 추천 레시피 정보를 반환합니다.
     */

    public List<RecipeResponseDto> getContentBasedRecommendation(Long recipeId) {
        RecommendationsEntity recommendations = recommendationsRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_RECOMMEND_RECIPE));

        List<RecipeEntity> recommendedRecipes = new ArrayList<>();
        RecipeEntity[] recipes = {recommendations.getFirst(), recommendations.getSecond(),
                recommendations.getThird(), recommendations.getForth(), recommendations.getFifth()};

        for (RecipeEntity recipe : recipes) {
            if (recipe != null) {
                recommendedRecipes.add(recipe);
            }
        }

        return recommendedRecipes.stream()
                .map(recipe -> new RecipeResponseDto(recipe.getRecipeId(), recipe.getName(), recipe.getMinutes()))
                .collect(Collectors.toList());
    }


    /**
     * 특정 레시피 ID 리스트로 레시피 이름과 시간을 반환합니다.
     */
    public List<RecipeResponseDto> getRecipesByIds(List<Long> recipeIds) {
        List<RecipeEntity> recipes = recipeRepository.findAllById(recipeIds);

        return recipes.stream()
                .map(recipe -> new RecipeResponseDto(
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
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

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
