package Software.SoftwareApplication.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class UserRatingsId implements Serializable {

    @Column(name = "recipe_id")
    private Long recipeId;

    @Column(name = "user_id")
    private Long userId;

    public UserRatingsId(Long recipeId, Long userId) {
        this.recipeId = recipeId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRatingsId that = (UserRatingsId) o;
        return Objects.equals(recipeId, that.recipeId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipeId, userId);
    }
}
