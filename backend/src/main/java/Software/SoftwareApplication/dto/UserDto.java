package Software.SoftwareApplication.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private String id;
    private String password;
    private List<RatingDto> ratings;

    public UserDto(String id, String password, List<RatingDto> ratings){
        this.id = id;
        this.password = password;
        this.ratings = ratings;
    }
}
