package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.dto.RatingDto;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.entity.UserRatingsId;
import Software.SoftwareApplication.repository.UserRepository;
import Software.SoftwareApplication.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        UserEntity userEntity = userRepository.findById(request.getUser_id());
        if (userEntity == null) {
            userEntity = new UserEntity(request.getUser_id(), request.getPassword());
            userEntity = userRepository.save(userEntity);  // 새로운 사용자 저장
        }

        // 평점 정보 저장
        for (RatingDto ratingDto : request.getRatings()) {
            UserRatingsId userRatingsId = new UserRatingsId(ratingDto.getRecipeId(), userEntity.getUserId());
            UserRatingsEntity userRatings = new UserRatingsEntity(userRatingsId, ratingDto.getRating());
            ratingRepository.save(userRatings);
        }
    }
}
