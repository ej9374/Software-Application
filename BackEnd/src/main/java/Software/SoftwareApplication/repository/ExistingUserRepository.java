package Software.SoftwareApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import Software.SoftwareApplication.entity.ExistingUserEntity;

public interface ExistingUserRepository extends JpaRepository<ExistingUserEntity, Integer> {
    // JpaRepository는 기본적으로 existsById 메서드를 제공
}
