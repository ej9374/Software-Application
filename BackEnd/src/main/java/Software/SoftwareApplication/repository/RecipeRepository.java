package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<RecipeEntity, Long> {
    // JPA 기본 메서드 활용 (findAllById)
}
