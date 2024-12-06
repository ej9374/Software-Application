package Software.SoftwareApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Software.SoftwareApplication.entity.ExistingUserEntity;

public interface ExistingUserRepository extends JpaRepository<ExistingUserEntity, Integer> {
    ExistingUserEntity findByUserId(Integer userId);
}
