package Software.SoftwareApplication.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {
    private String id;
    private String password;
    private List<RatingDto> ratings;
}
