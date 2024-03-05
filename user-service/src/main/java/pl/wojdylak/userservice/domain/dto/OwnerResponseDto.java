package pl.wojdylak.userservice.domain.dto;

public record OwnerResponseDto(
        Long id,
        String name,
        String payUClientId,
        String payUClientSecret
) {
}