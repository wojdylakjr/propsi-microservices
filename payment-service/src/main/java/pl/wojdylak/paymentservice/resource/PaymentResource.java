package pl.wojdylak.paymentservice.resource;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wojdylak.paymentservice.domain.CreatePaymentDto;
import pl.wojdylak.paymentservice.domain.Payment;
import pl.wojdylak.paymentservice.domain.payu.PayUAddOrderResponse;
import pl.wojdylak.paymentservice.domain.payu.PayUPaymentNotification;
import pl.wojdylak.paymentservice.service.PaymentService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/payments")
public class PaymentResource {
    private final PaymentService paymentService;

    @PostMapping()
    public PayUAddOrderResponse createPaymentTransaction(@RequestBody CreatePaymentDto createPaymentDto) {

        return paymentService.createPaymentTransaction(createPaymentDto);
    }

    @GetMapping("/{id}")
    public Payment findPaymentById(@PathVariable String id) {

        return paymentService.findById(id);
    }

    @GetMapping
    public List<Payment> findAll() {

        return paymentService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deletePaymentById(@PathVariable String id) {

        paymentService.deleteById(id);
    }

    @DeleteMapping
    public void deleteAll() {

        paymentService.deleteAll();
    }

    @PostMapping("/notify")
    public void addPaymentResponse(@RequestBody PayUPaymentNotification paymentNotification) {

        if (!"COMPLETED".equals(paymentNotification.order().status())) {
            throw new RuntimeException("Payment is not completed");
        }

        paymentService.updatePayment(paymentNotification);
    }
}
