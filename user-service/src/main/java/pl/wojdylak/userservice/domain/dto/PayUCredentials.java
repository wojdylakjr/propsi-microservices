package pl.wojdylak.userservice.domain.dto;

public record PayUCredentials(
        String payUClientId,
        String payUClientSecret
) {
}