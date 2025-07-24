package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    private int userId;

    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "refreshToken")
    private String refreshToken;

    public UserEntity(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
