package Software.SoftwareApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.entity.UserRatingsId;

@Repository
public interface RatingRepository extends JpaRepository<UserRatingsEntity, UserRatingsId> {
}
