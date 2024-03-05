package pl.wojdylak.paymentservice.domain;

public record CreatePaymentDto(Long billId, Long tenantId) {
}
