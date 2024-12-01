package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.entity.UserRatingsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<UserRatingsEntity, UserRatingsId> {
}
