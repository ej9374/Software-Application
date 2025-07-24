package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.MatchRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchRecipeRepository extends JpaRepository<MatchRecipeEntity, Integer> {
    MatchRecipeEntity findByNewRecipeId(Integer newRecipeId);
}
