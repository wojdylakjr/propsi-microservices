package pl.wojdylak.paymentservice.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class UserServiceClient {
    private static final String USER_SERVICE_URL = "http://localhost:8081";

    private final RestTemplate restTemplate;

    public OwnerResponseDto getOwnerById(Long ownerId) {

        String url = USER_SERVICE_URL + "/owners/" + ownerId;
        ParameterizedTypeReference<OwnerResponseDto> responseType = new ParameterizedTypeReference<>() {
        };

        return send(HttpMethod.GET,
                url,
                responseType,
                null);
    }

    private <T> T send(HttpMethod httpMethod,
                       String url,
                       ParameterizedTypeReference<T> responseType,
                       Object body) {

        RequestEntity<?> request = RequestEntity
                .method(httpMethod, url)
                .body(body);

        return restTemplate.exchange(request, responseType)
                .getBody();
    }
}
