package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.dto.RatingDto;
import Software.SoftwareApplication.entity.RecipeEntity;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.global.exception.base.CustomException;
import Software.SoftwareApplication.global.exception.base.ErrorCode;
import Software.SoftwareApplication.repository.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final UserRatingsRepository userRatingsRepository;
    private final ExistingUserRepository existingUserRepository;

    @Value("z{jwt.secret}")
    private SecretKey secretKey;

    /**
     * 사용자 등록 메서드
     */
    @Transactional
    public void registerUser(SignUpRequestDto request) {
        // ID로 사용자 찾기
        if (userRepository.findById(request.getId()).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }

        // 새로운 사용자 저장
        UserEntity user = new UserEntity();
        user.setId(request.getId()); // ID 설정

        // 비밀번호 해싱 필요 + jwt filter 추가

        user.setPassword(request.getPassword());
        userRepository.save(user);

        // RatingDto의 RecipeId로 RecipeEntity 찾아서저장?해야됨

        // 평점 정보 저장
        for (RatingDto ratingDto : request.getRatings()) {

            Long recipeId = ratingDto.getRecipeId();
            Integer rating = ratingDto.getRating();

            if (recipeId == null || rating == null) {
                throw new CustomException(ErrorCode.NULL_RATING_VALUE);
            }

            RecipeEntity recipe = recipeRepository.findByRecipeId(recipeId);

            if (recipe == null)
                throw new CustomException(ErrorCode.RECIPE_NOT_FOUND);

            UserRatingsEntity userRatings = new UserRatingsEntity();

            userRatings.setUser(user);
            userRatings.setRecipe(recipe);
            userRatings.setRating(rating);

            userRatingsRepository.save(userRatings);
        }
    }

    /**
     * 사용자 로그인 메서드 - ID를 기반으로 검증 (String -> int 변환 포함)
     */
    public int validateUser(String id) {
        try {
            // ID를 int로 변환
            int userId = Integer.parseInt(id);

            // existing_user 테이블에서 userId 확인
            if (existingUserRepository.existsById(userId)) {
                return userId; // userId 반환
            } else {
                throw new RuntimeException("존재하지 않는 사용자 ID입니다.");
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("ID는 숫자 형식이어야 합니다.", e);
        }
    }


    /**
     * JWT 토큰 생성
     */
    public String generateToken(int userId) {
        return Jwts.builder()
                .setSubject(String.valueOf(userId)) // 사용자 ID를 토큰의 주체로 설정
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)) // 토큰 만료 시간 (24시간)
                .signWith(secretKey, SignatureAlgorithm.HS256) // 서명 알고리즘 및 비밀 키 설정
                .compact();
    }

    /**
     * JWT 토큰 검증
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true; // 토큰이 유효하면 true 반환
        } catch (Exception e) {
            return false; // 토큰이 유효하지 않으면 false 반환
        }
    }
}
