package pl.wojdylak.rentalservice.property.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@AllArgsConstructor
public class PropertyServiceClient {
    private static final String PROPERTY_SERVICE_URL = "http://localhost:8082";

    private final RestTemplate restTemplate;

    public List<PremisesCostsDetailsResponseDto> getPremisesCostsInMonth(Long premisesId,
                                                                         int monthValue) {

        String url = PROPERTY_SERVICE_URL + "/premises/" + premisesId + "/costs?monthValue=" + monthValue;
        ParameterizedTypeReference<List<PremisesCostsDetailsResponseDto>> responseType = new ParameterizedTypeReference<>() {
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
