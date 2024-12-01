package Software.SoftwareApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailedRecipeResponseDto {
    private Long recipeId;       // 레시피 ID
    private String name;         // 레시피 이름
    private Integer minutes;     // 조리 시간
    private String tags;         // 태그
    private String nutrition;    // 영양 정보
    private Integer nSteps;      // 조리 단계 수
    private String steps;        // 조리 단계
    private String description;  // 레시피 설명
    private String ingredients;  // 재료 목록
    private Integer nIngredients; // 재료 수
}
