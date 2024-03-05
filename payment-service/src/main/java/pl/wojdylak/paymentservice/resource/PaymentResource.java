package pl.wojdylak.paymentservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wojdylak.paymentservice.domain.CreatePaymentDto;
import pl.wojdylak.paymentservice.domain.payu.PayUAddOrderResponse;
import pl.wojdylak.paymentservice.domain.payu.PayUPaymentNotification;
import pl.wojdylak.paymentservice.service.PaymentService;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentResource {
    private final PaymentService paymentService;

    @PostMapping()
    public PayUAddOrderResponse createPaymentTransaction(@RequestBody CreatePaymentDto createPaymentDto) {

        return paymentService.createPaymentTransaction(createPaymentDto);
    }

    @PostMapping("/notify")
    public void addPaymentResponse(@RequestBody PayUPaymentNotification paymentNotification) {

        if (!"COMPLETED".equals(paymentNotification.order().status())) {
            throw new RuntimeException("Payment is not completed");
        }

        paymentService.savePaymentResponse(paymentNotification);
    }
}
