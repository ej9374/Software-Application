package Software.SoftwareApplication.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@RequiredArgsConstructor
public class RatingDto {

    private Long recipeId;

    @Min(0)
    @Max(5)
    private Integer rating;

}

