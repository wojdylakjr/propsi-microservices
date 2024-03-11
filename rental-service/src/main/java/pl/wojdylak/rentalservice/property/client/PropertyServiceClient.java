package pl.wojdylak.rentalservice.property.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PropertyServiceClient {
    @Value("${property.service.url}")
    private String propertyServiceUrl;

    private final RestTemplate restTemplate;

    public List<PremisesCostsDetailsResponseDto> getPremisesCostsInMonth(Long premisesId,
                                                                         int monthValue) {

        String url = propertyServiceUrl + "/premises/" + premisesId + "/costs?monthValue=" + monthValue;
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
