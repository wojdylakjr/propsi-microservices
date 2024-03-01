package pl.wojdylak.rentalservice.domain.dto;

public record CreateBillDto(
        Long rentalId,
        Integer monthValue
) {
}
