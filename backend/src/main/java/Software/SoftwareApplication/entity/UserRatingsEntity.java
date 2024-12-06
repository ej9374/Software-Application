package Software.SoftwareApplication.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_ratings")
public class UserRatingsEntity {

    @MapsId("userId")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @MapsId("recipeId")
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private RecipeEntity recipe;

    @EmbeddedId
    private UserRatingsId id;

    @Column(name = "rating", nullable = false)
    @Min(0)
    @Max(5)
    private Integer rating;

    public UserRatingsEntity(UserRatingsId id, Integer rating) {
        this.id = id;
        this.rating = rating;
    }
}
