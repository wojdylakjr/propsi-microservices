package pl.wojdylak.paymentservice.service;

import org.springframework.stereotype.Component;
import pl.wojdylak.paymentservice.client.OwnerResponseDto;
import pl.wojdylak.paymentservice.domain.Owner;
import pl.wojdylak.paymentservice.domain.payu.PayUTokenResponse;

@Component
public class OwnerCreator {
    public Owner create(OwnerResponseDto ownerResponseDto,
                        PayUTokenResponse payUTokenResponse) {

        return Owner.builder()
                .id(ownerResponseDto.id())
                .payUClientId(ownerResponseDto.payUClientId())
                .payUClientSecret(ownerResponseDto.payUClientSecret())
                .payUAccessToken(payUTokenResponse.accessToken())
                .payUAccessTokenExpiration(payUTokenResponse.expiresIn())
                .build();
    }
}
