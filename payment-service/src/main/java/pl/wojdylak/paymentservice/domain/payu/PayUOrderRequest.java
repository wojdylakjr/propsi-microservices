package pl.wojdylak.paymentservice.domain.payu;

import lombok.Builder;

import java.util.List;

@Builder
public record PayUOrderRequest(
        String notifyUrl,
        String customerIp,
        String merchantPosId,
        String description,
        String currencyCode,
        String totalAmount,
        String continueUrl,
        String extOrderId,
        Buyer buyer,
        List<Product> products

) {

    public record Buyer(
            String email,
            String firstName,
            String lastName
    ) {
    }

    public record Product(
            String name,
            String unitPrice,
            String quantity

    ) {
    }
}
