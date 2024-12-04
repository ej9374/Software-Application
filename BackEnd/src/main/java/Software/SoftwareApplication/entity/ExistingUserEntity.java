package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "existing_user")
public class ExistingUserEntity {

    @Id
    @Column(name = "user_id")
    private Integer user;

    @Column(name = "new_user_id")
    private Integer newUserId;
}
