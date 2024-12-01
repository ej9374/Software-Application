package Software.SoftwareApplication.api;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class KaggleApi {
    private final RestTemplate restTemplate;

    public KaggleApi(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Long> sendRatings(UserRatingDto userRatingDto) {
        ResponseEntity<List<Long>> response = restTemplate.postForEntity(
                "https://kaggle-api.example.com/rate",
                userRatingDto,
                new ParameterizedTypeReference<List<Long>>() {}
        );
        return response.getBody();
    }

    public List<Long> getRecommendations(String userId, Long recipeId) {
        Map<String, Object> payload = Map.of("userId", userId, "recipeId", recipeId);
        ResponseEntity<List<Long>> response = restTemplate.postForEntity(
                "https://kaggle-api.example.com/recommend",
                payload,
                new ParameterizedTypeReference<List<Long>>() {}
        );
        return response.getBody();
    }
}

