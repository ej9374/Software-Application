package Software.SoftwareApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Software.SoftwareApplication.entity.ExistingUserEntity;

import java.util.Optional;

public interface ExistingUserRepository extends JpaRepository<ExistingUserEntity, Integer> {
    Optional<ExistingUserEntity> findByUserId(String userId);
}
