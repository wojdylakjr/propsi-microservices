package pl.wojdylak.paymentservice.client;

import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class RentalServiceClient {
    private static final String RENTAL_SERVICE_URL = "http://localhost:8083";

    private final RestTemplate restTemplate;

    public BillResponseDto getBillById(Long billId) {

        String url = RENTAL_SERVICE_URL + "/bills/" + billId;
        ParameterizedTypeReference<BillResponseDto> responseType = new ParameterizedTypeReference<>() {
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
