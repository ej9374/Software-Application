package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_ratings")
public class UserRatingsEntity {

    @EmbeddedId
    private UserRatingsId id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    public UserRatingsEntity(UserRatingsId id, Integer rating) {
        this.id = id;
        this.rating = rating;
    }
}
