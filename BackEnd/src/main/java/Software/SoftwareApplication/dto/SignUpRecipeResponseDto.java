package Software.SoftwareApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRecipeResponseDto {
    private Long recipeId;       // 레시피 ID
    private String name;         // 레시피 이름
    private Integer minutes;     // 조리 시간
    private String tags;         // 태그
    private Integer nSteps;      // 단계 수
    private String description;  // 설명
    private String ingredients;  // 재료
}
