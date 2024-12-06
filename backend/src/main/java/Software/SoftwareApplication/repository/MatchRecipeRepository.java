package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.MatchRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRecipeRepository extends JpaRepository<MatchRecipeEntity, Integer> {
    MatchRecipeEntity findByNewRecipeId(Integer newRecipeId);

    // 추천된 ID 리스트로 레시피 ID 리스트를 가져오는 메서드
    @Query("SELECT m.recipe.recipeId FROM MatchRecipeEntity m WHERE m.newRecipeId IN :newRecipeIds")
    List<Long> getRecipeIdsByNewRecipeIds(@Param("newRecipeIds") List<Integer> newRecipeIds);

}
