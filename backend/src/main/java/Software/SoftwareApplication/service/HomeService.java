package Software.SoftwareApplication.service;

import Software.SoftwareApplication.api.FlaskApiClient;
import Software.SoftwareApplication.dto.HomeResponseDto;
import Software.SoftwareApplication.repository.ExistingUserRepository;
import Software.SoftwareApplication.repository.MatchRecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService {

    private static final Logger logger = LoggerFactory.getLogger(HomeService.class);

    private final ExistingUserRepository existingUserRepository;
    private final MatchRecipeRepository matchRecipeRepository;
    private final FlaskApiClient flaskApiClient;
    private final RecipeService recipeService;

    public HomeService(ExistingUserRepository existingUserRepository,
                       MatchRecipeRepository matchRecipeRepository,
                       FlaskApiClient flaskApiClient,
                       RecipeService recipeService) {
        this.existingUserRepository = existingUserRepository;
        this.matchRecipeRepository = matchRecipeRepository;
        this.flaskApiClient = flaskApiClient;
        this.recipeService = recipeService;
    }

    /**
     * 사용자 ID를 기반으로 추천된 레시피 목록을 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 추천된 레시피 정보 목록
     */
    public List<HomeResponseDto> getHomeRecipes(Integer userId) {
        List<Long> recipeIds = getRecommendedRecipeIds(userId);

        // RecipeService를 사용하여 실제 레시피 데이터를 가져옴
        return recipeService.getRecipesByIds(recipeIds);
    }

    /**
     * Flask 서버 및 DB를 통해 추천 레시피 ID를 가져옵니다.
     *
     * @param userId 사용자 ID
     * @return 추천된 레시피 ID 목록
     */
    private List<Long> getRecommendedRecipeIds(Integer userId) {
        List<Long> recipeIds = new ArrayList<>();

        try {
            // 사용자 ID로 새로운 User ID 조회
            Integer newUserId = existingUserRepository.findByUserId(userId).getNewUserId();

            // Flask 서버에서 추천된 newRecipeIds 가져오기
            List<Integer> recommendedIds = flaskApiClient.getRecommendations(newUserId);

            // 추천된 newRecipeIds를 기반으로 매칭된 recipeIds 가져오기
            if (recommendedIds != null && !recommendedIds.isEmpty()) {
                recipeIds = matchRecipeRepository.getRecipeIdsByNewRecipeIds(recommendedIds);
            }
        } catch (Exception e) {
            logger.error("Failed to fetch recommendations from Flask server for userId: {}", userId, e);
        }

        // 추천 결과가 없을 경우 기본값 설정
        if (recipeIds.isEmpty()) {
            recipeIds = List.of(137739L, 31490L, 112140L, 59389L, 44061L, 5289L, 25274L, 67888L, 70971L, 75452L);
            logger.warn("No recommendations found for userId: {}. Using default recommendations.", userId);
        }

        return recipeIds;
    }
}
