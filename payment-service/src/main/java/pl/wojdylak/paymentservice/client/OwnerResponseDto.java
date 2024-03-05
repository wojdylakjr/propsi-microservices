package pl.wojdylak.paymentservice.client;

public record OwnerResponseDto(
        Long id,
        String name,
        String payUClientId,
        String payUClientSecret
) {
}