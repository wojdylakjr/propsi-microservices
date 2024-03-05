package pl.wojdylak.paymentservice.domain.payu;

public record PayUAddOrderResponse(
        Status status,
        String redirectUri,
        String orderId,
        String extOrderId
) {
    private record Status(
            String statusCode
    ) {
    }
}