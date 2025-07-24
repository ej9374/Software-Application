package Software.SoftwareApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {

    @Schema(example = "user123")
    private String id;

    @Schema(example = "password123")
    private String password;

    @Schema(example = "[{\"recipeId\": 1, \"rating\": 4.5}, " +
            "{\"recipeId\": 2, \"rating\": 3.0}, " +
            "{\"recipeId\": 3, \"rating\": 5.0}, " +
            "{\"recipeId\": 4, \"rating\": 2.5}, " +
            "{\"recipeId\": 5, \"rating\": 4.0}]")
    private List<RatingDto> ratings;
}
