package pl.wojdylak.paymentservice.domain.payu;

import java.util.ArrayList;
import java.util.Date;

public record PayUPaymentNotification(
        Order order,
        Date localReceiptDateTime,
        ArrayList<Property> properties
) {

    public record Property(
            String name,
            String value

    ) {
    }

    public record Order(
            String orderId,
            String extOrderId,
            Date orderCreateDate,
            String notifyUrl,
            String customerIp,
            String merchantPosId,
            String description,
            String currencyCode,
            String totalAmount,
            Buyer buyer,
            PayMethod payMethod,
            String status,
            ArrayList<Product> products
    ) {

        private record Buyer(
                String customerId,
                String email,
                String firstName,
                String lastName
        ) {
        }

        public record PayMethod(
                String amount,
                String type
        ) {
        }

        private record Product(
                String name,
                String unitPrice,
                String quantity

        ) {
        }
    }
}


