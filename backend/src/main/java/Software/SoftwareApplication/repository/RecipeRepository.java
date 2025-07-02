package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    // 모든 Recipe ID를 반환하는 메서드
    @Query("SELECT r.recipeId FROM RecipeEntity r")
    List<Long> findAllIds();

    RecipeEntity findByRecipeId(Long recipeId);
}
