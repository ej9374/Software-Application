package Software.SoftwareApplication.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignUpRequestDto {
    private String id;
    private String password;
    private String email;
    private List<RatingDto> ratings;

    public SignUpRequestDto() {}

    public SignUpRequestDto(String id, String password, String email, List<RatingDto> ratings) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.ratings = ratings;
    }
}
