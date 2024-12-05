package Software.SoftwareApplication.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class FlaskApiClient {

    private final RestTemplate restTemplate;

    // Flask 서버의 URL을 application.properties 또는 application.yml에서 설정
    @Value("${flask.server.url}")
    private String flaskServerUrl;

    public FlaskApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Integer> getRecommendations(Integer userId) {
        try {
            // 요청 본문 생성
            Map<String, Object> requestBody = Map.of("user_ids", List.of(userId));

            // HTTP 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // HTTP 요청 엔티티 생성
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Flask 서버로 POST 요청 보내기
            ResponseEntity<Map> response = restTemplate.postForEntity(flaskServerUrl + "/recommend", requestEntity, Map.class);

            // 응답이 성공적일 경우 데이터 파싱
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                Map<String, List<Integer>> recommendations = (Map<String, List<Integer>>) response.getBody();
                return recommendations.getOrDefault(userId.toString(), List.of());
            }
        } catch (Exception e) {
            System.err.println("Error calling Flask server: " + e.getMessage());
        }

        // Flask 호출 실패 시 빈 리스트 반환
        return List.of();
    }


}

