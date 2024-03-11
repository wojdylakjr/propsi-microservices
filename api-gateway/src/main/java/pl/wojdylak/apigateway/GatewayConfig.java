package pl.wojdylak.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${user.service.url}")
    private String userServiceUrl;

    @Value("${property.service.url}")
    private String propertyServiceUrl;

    @Value("${rental.service.url}")
    private String rentalServiceUrl;

    @Value("${payment.service.url}")
    private String paymentServiceUrl;

    @Bean
    public RouteLocator routerBuilder(RouteLocatorBuilder routeLocatorBuilder) {

        return routeLocatorBuilder.routes()
                .route("user-service-users", r -> r.path(
                                "/users/**",
                                "/owners/**")
                        .uri(userServiceUrl))
                .route("property-service", r -> r.path(
                                "/properties/**",
                                "/meter-measurements/**",
                                "/premises-costs-details/**",
                                "/premises/**")
                        .uri(propertyServiceUrl))
                .route("rental-service", r -> r.path(
                                "/rentals/**",
                                "/bills/**")
                        .uri(rentalServiceUrl))
                .route("payment-service", r -> r.path(
                                "/payments/**")
                        .uri(paymentServiceUrl))
                .build();
    }
}
