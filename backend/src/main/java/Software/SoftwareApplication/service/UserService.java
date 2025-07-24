package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.dto.RatingDto;
import Software.SoftwareApplication.entity.ExistingUserEntity;
import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.global.exception.base.CustomException;
import Software.SoftwareApplication.global.exception.base.ErrorCode;
import Software.SoftwareApplication.global.exception.custom.EntityNotFoundException;
import Software.SoftwareApplication.global.security.jwt.JwtProvider;
import Software.SoftwareApplication.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final UserRatingsRepository userRatingsRepository;
    private final ExistingUserRepository existingUserRepository;

    /**
     * 사용자 등록 메서드
     */
    @Transactional
    public void signUp(SignUpRequestDto request) {
        // ID로 사용자 찾기
        userRepository.findById(request.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.DUPLICATE_ID));

        // 새로운 사용자 저장
        UserEntity user = new UserEntity();
        user.setId(request.getId()); // ID 설정
        user.setPassword(passwordEncoder.encode(request.getPassword())); //해싱한 비밀번호

        userRepository.save(user);

        // 평점 정보 저장
        for (RatingDto ratingDto : request.getRatings()) {

            Long recipeId = ratingDto.getRecipeId();
            Integer rating = ratingDto.getRating();

            if (recipeId == null || rating == null) {
                throw new CustomException(ErrorCode.NULL_RATING_VALUE);
            }

            RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId)
                    .orElseThrow(() -> new EntityNotFoundException("recipeId", recipeId));

            UserRatingsEntity userRatings = new UserRatingsEntity();

            userRatings.setUser(user);
            userRatings.setRecipe(recipe);
            userRatings.setRating(rating);

            userRatingsRepository.save(userRatings);
        }
    }

    @Transactional
    public Map<String, String> login(String id, String password) {
//        ExistingUserEntity existingUser = existingUserRepository.findByUserId(id)
//                .orElseThrow(() -> new CustomException(ErrorCode.NO_DATABASE_USER));

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIALS));


        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_CREDENTIALS);
        }

        //성공시 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = user.getRefreshToken();

        if (refreshToken == null) {
            refreshToken = jwtProvider.createRefreshToken(user.getId());
            user.setRefreshToken(refreshToken);
            userRepository.save(user);
        }

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

    @Transactional
    public Map<String, String> refreshAccessToken(String refreshToken) {
        if (!jwtProvider.validationRefreshToken(refreshToken)){
            throw new CustomException(ErrorCode.JWT_TOKEN_EXPIRED);
        }

        String id = jwtProvider.extractId(refreshToken);
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_JWT_REFRESH_TOKEN));

        if (!refreshToken.equals(user.getRefreshToken())) {
            user.updateRefreshToken(null);
            userRepository.save(user);
            throw new CustomException(ErrorCode.JWT_TOKEN_EXPIRED);
        }

        String accessToken = jwtProvider.createAccessToken(id);

        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        return map;
    }

    public void logout(String id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateRefreshToken(null);
        userRepository.save(user);
    }


}