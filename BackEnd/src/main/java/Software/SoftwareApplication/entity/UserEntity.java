package Software.SoftwareApplication.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "id", length = 50, nullable = false)
    private String id;

    @Column(name = "password",  length = 255, nullable = false)
    private String password;

    public UserEntity() {} // 기본 생성자

    public UserEntity(Long userId, String id, String password){
        this.userId = userId;
        this.id = id;
        this.password = password;
    }
}
