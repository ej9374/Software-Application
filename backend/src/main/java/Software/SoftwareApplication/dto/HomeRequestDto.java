package Software.SoftwareApplication.dto;

public class HomeRequestDto {
    private Integer userId;

    // Jackson에서 사용할 기본 생성자 필요
    public HomeRequestDto() {}

    // Getter and Setter
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
