package Software.SoftwareApplication.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class FlaskApiClient {

    private final RestTemplate restTemplate;

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public FlaskApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<List<Integer>> getRecommendations(Integer userId) { // 반환 타입을 List<List<Integer>>로 수정
        try {
            // 요청 본문 생성
            Map<String, Object> requestBody = Map.of("user_id", userId);  // userIds 그대로 전달

            // 요청 데이터 로그 출력
            System.out.println("Sending request body: " + requestBody);  // 보내는 데이터 로그 출력

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HTTP 요청 엔티티 생성
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Flask 서버로 POST 요청 보내기
            ResponseEntity<Map<String, List<List<Integer>>>> response = restTemplate.exchange(
                    flaskServerUrl + "/recommend",
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<Map<String, List<List<Integer>>>>() {}
            );

            // 응답이 성공적일 경우 데이터 파싱
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, List<List<Integer>>> recommendations = response.getBody();
                if (recommendations.containsKey(userId.toString())) { // 첫 번째 userId 확인
                    return recommendations.get(userId.toString());
                } else {
                    System.err.println("No recommendations found for user IDs: " + userId);
                }
            }
        } catch (Exception e) {
            System.err.println("Error calling Flask server: " + e.getMessage());
            e.printStackTrace();
        }

        // Flask 호출 실패 시 빈 리스트 반환
        return List.of();
    }
}
