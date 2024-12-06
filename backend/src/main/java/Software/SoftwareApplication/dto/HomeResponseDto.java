package Software.SoftwareApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HomeResponseDto {
    private Long recipeId;   // 레시피 ID
    private String name;     // 레시피 이름
    private Integer minutes; // 조리 시간
}
