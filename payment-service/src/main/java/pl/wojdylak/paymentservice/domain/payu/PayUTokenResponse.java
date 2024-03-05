package pl.wojdylak.paymentservice.domain.payu;

import com.fasterxml.jackson.annotation.JsonAlias;

public record PayUTokenResponse(
        @JsonAlias("access_token")
        String accessToken,
        @JsonAlias("token_type")
        String tokenType,
        @JsonAlias("expires_in")
        Long expiresIn,
        @JsonAlias("grant_type")
        String grantType
) {
}
