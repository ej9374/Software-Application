package Software.SoftwareApplication.service;

import Software.SoftwareApplication.dto.SignUpRequestDto;
import Software.SoftwareApplication.dto.RatingDto;
import Software.SoftwareApplication.entity.UserEntity;
import Software.SoftwareApplication.entity.UserRatingsEntity;
import Software.SoftwareApplication.entity.UserRatingsId;
import Software.SoftwareApplication.repository.ExistingUserRepository;
import Software.SoftwareApplication.repository.UserRepository;
import Software.SoftwareApplication.repository.RatingRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final ExistingUserRepository existingUserRepository;
    private final SecretKey secretKey;

    @Autowired
    public UserService(UserRepository userRepository,
                       RatingRepository ratingRepository,
                       ExistingUserRepository existingUserRepository,
                       @Value("${jwt.secret}") String secret) {
        this.userRepository = userRepository;
        this.ratingRepository = ratingRepository;
        this.existingUserRepository = existingUserRepository;

        // JWT 비밀 키 초기화
        if (secret == null || secret.length() < 32) {
            throw new IllegalArgumentException("JWT 비밀 키는 최소 32바이트 이상이어야 합니다.");
        }
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 사용자 등록 메서드
     */
    @Transactional
    public void registerUser(SignUpRequestDto request) {
        // ID로 사용자 찾기
        if (userRepository.findById(request.getId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 사용자 ID입니다.");
        }

        // 새로운 사용자 저장
        UserEntity userEntity = new UserEntity();
        userEntity.setId(request.getId()); // ID 설정
        userEntity.setPassword(request.getPassword());
        userRepository.save(userEntity);

        // 평점 정보 저장
        for (RatingDto ratingDto : request.getRatings()) {
            if (ratingDto.getRecipeId() == null || ratingDto.getRating() == null) {
                throw new IllegalArgumentException("레시피 ID 또는 평점이 비어 있습니다.");
            }

            UserRatingsId userRatingsId = new UserRatingsId(ratingDto.getRecipeId(), userEntity.getUserId());
            UserRatingsEntity userRatings = new UserRatingsEntity(userRatingsId, ratingDto.getRating());
            ratingRepository.save(userRatings);
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
