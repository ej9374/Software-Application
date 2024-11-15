package Software.SoftwareApplication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.dto.RatingDto;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.entity.UserRatingsId;
import Software.SoftwareApplication.repository.UserRepository;
import Software.SoftwareApplication.repository.RatingRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;

    @Autowired
    public UserService(UserRepository userRepository, RatingRepository ratingRepository) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
    }

    @Transactional
    public void registerUser(SignUpRequestDto request) {
        // 사용자 정보 저장
        UserEntity userEntity = new UserEntity(null, request.getId(), request.getPassword());
        userEntity = userRepository.save(userEntity);  // User를 먼저 저장하여 ID 생성

        // 평점 정보 저장
        for (RatingDto ratingDto : request.getRatings()) {
            UserRatingsId userRatingsId = new UserRatingsId(ratingDto.getRecipeId(), userEntity.getUserId());
            UserRatingsEntity userRatings = new UserRatingsEntity(userRatingsId, ratingDto.getRating());
            ratingRepository.save(userRatings);
        }
    }
}
