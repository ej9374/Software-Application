package Software.SoftwareApplication.dto;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class RatingDto {
    @JsonProperty("recipeId") // JSON 필드명 매핑
    private Long recipeId;

    @JsonProperty("rating") // JSON 필드명 매핑
    private Integer rating;

    public RatingDto(Long recipeId, Integer rating) {
        this.recipeId = recipeId;
        this.rating = rating;
    }
}

