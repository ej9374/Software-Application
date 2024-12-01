package Software.SoftwareApplication.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingDto {
    private Long recipeId;
    private Integer rating;

    public RatingDto(Long recipeId, Integer rating) {
        this.recipeId = recipeId;
        this.rating = rating;
    }
}
