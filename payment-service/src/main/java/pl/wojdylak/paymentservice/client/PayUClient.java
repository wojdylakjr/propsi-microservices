package pl.wojdylak.paymentservice.client;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import pl.wojdylak.paymentservice.domain.payu.PayUAddOrderResponse;
import pl.wojdylak.paymentservice.domain.payu.PayUOrderRequest;
import pl.wojdylak.paymentservice.domain.payu.PayUTokenResponse;


@Component
@AllArgsConstructor
public class PayUClient {
    private static final String PAYU_URL = "https://secure.snd.payu.com/";
    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public PayUTokenResponse authenticate(String clientId,
                                          String clientSecret) {

        String url = PAYU_URL + "pl/standard/user/oauth/authorize?grant_type=client_credentials&" +
                "client_id=" + clientId +
                "&client_secret=" + clientSecret;
        ParameterizedTypeReference<PayUTokenResponse> responseType = new ParameterizedTypeReference<>() {
        };

        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return send(HttpMethod.GET,
                    url,
                    responseType,
                    null,
                    null);
        } finally {
            sample.stop(meterRegistry.timer("payu.authenticate.time"));
        }
    }

    public PayUAddOrderResponse addOrder(PayUOrderRequest request,
                                         String accessToken) {

        String url = PAYU_URL + "api/v2_1/orders";
        ParameterizedTypeReference<PayUAddOrderResponse> responseType = new ParameterizedTypeReference<>() {
        };

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        headers.set("Authorization", "Bearer " + accessToken);
        Timer.Sample sample = Timer.start(meterRegistry);
        try {
            return send(HttpMethod.POST,
                    url,
                    responseType,
                    request,
                    headers);
        } finally {
            sample.stop(meterRegistry.timer("payu.add_order.time"));
        }
    }

    private <T> T send(HttpMethod httpMethod,
                       String url,
                       ParameterizedTypeReference<T> responseType,
                       Object body,
                       HttpHeaders headers) {

        RequestEntity<?> request = RequestEntity
                .method(httpMethod, url)
                .headers(headers)
                .body(body);

        return restTemplate.exchange(request, responseType)
                .getBody();
    }
}
