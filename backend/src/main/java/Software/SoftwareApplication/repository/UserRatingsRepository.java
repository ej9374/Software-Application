package Software.SoftwareApplication.repository;

import Software.SoftwareApplication.entity.UserRatingsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRatingsRepository extends JpaRepository<UserRatingsEntity, Long> {

}
