package pl.wojdylak.paymentservice.service;

import org.springframework.stereotype.Component;
import pl.wojdylak.paymentservice.client.BillResponseDto;
import pl.wojdylak.paymentservice.client.OwnerResponseDto;
import pl.wojdylak.paymentservice.domain.payu.PayUOrderRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class PayUOrderRequestCreator {
    private final static String NGROK_URL = "https://201f-78-8-49-114.eu.ngrok.io";
    private final static String NOTIFY_URL = NGROK_URL + "/payments/notify";
    private final static String CUSTOMER_IP = "127.0.0.1";
    private final static String CURRENCY_CODE = "PLN";

    public PayUOrderRequest create(BillResponseDto bill,
                                   OwnerResponseDto owner) {

        PayUOrderRequest.Product product = new PayUOrderRequest.Product("Bill", convertToPayUFormat(bill.value()), "1");

        return PayUOrderRequest.builder()
                .extOrderId(bill.billId().toString())
                .description("Bill for " + bill.date().toString())
                .merchantPosId(owner.payUClientId())
                .currencyCode("PLN")
                .notifyUrl(NOTIFY_URL)
                .customerIp("127.0.126.126")
                .totalAmount(convertToPayUFormat(bill.value()))
                .products(List.of(product))
                .build();
    }

    private String convertToPayUFormat(BigDecimal number) {
        return number.multiply(BigDecimal.valueOf(100))
                .setScale(0, RoundingMode.UP)
                .toString();
    }
}
