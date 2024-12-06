package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.RecommendationsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationsRepository extends JpaRepository<RecommendationsEntity, Long> {
}
