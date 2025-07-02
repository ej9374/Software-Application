package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.entity.SignUpRecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignUpRecipeRepository extends JpaRepository<SignUpRecipeEntity, Long>{

    @Query("SELECT r.recipe FROM SignUpRecipeEntity r")
    List<RecipeEntity> findAllIds();
}
