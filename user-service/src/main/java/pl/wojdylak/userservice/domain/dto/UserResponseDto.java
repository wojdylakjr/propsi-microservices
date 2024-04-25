package pl.wojdylak.userservice.domain.dto;

public record UserResponseDto(
        Long id,
        String firstName,
        String lastName,
        String email
) {
}
