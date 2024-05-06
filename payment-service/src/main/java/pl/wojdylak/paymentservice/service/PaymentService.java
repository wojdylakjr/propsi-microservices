package pl.wojdylak.paymentservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wojdylak.paymentservice.client.*;
import pl.wojdylak.paymentservice.domain.CreatePaymentDto;
import pl.wojdylak.paymentservice.domain.Owner;
import pl.wojdylak.paymentservice.domain.Payment;
import pl.wojdylak.paymentservice.domain.payu.PayUAddOrderResponse;
import pl.wojdylak.paymentservice.domain.payu.PayUPaymentNotification;
import pl.wojdylak.paymentservice.domain.payu.PayUTokenResponse;
import pl.wojdylak.paymentservice.repository.PaymentRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {
    private final RentalServiceClient rentalServiceClient;
    private final UserServiceClient userServiceClient;
    private final PayUClient payUClient;
    private final OwnerCreator ownerCreator;
    private final OwnerService ownerService;
    private final PayUOrderRequestCreator payUOrderRequestCreator;
    private final PaymentRepository paymentRepository;

    public PayUAddOrderResponse createPaymentTransaction(CreatePaymentDto createPaymentDto) {

        BillResponseDto bill = rentalServiceClient.getBillById(createPaymentDto.billId());
        OwnerResponseDto ownerDto = userServiceClient.getOwnerById(bill.ownerId());
        PayUTokenResponse payUTokenResponse = payUClient.authenticate(ownerDto.payUClientId(), ownerDto.payUClientSecret());
        Owner owner = ownerCreator.create(ownerDto, payUTokenResponse);
        ownerService.save(owner);

        PayUAddOrderResponse payUAddOrderResponse = payUClient.addOrder(payUOrderRequestCreator.create(bill, ownerDto),
                payUTokenResponse.accessToken());

        Payment payment = Payment.builder()
                .id(payUAddOrderResponse.orderId())
                .date(Date.from(Instant.now()))
                .status("NOT_PAID")
                .build();

        paymentRepository.save(payment);

        return payUAddOrderResponse;
    }

    public void updatePayment(PayUPaymentNotification paymentNotification) {

//        Payment payment = paymentRepository.findById(paymentNotification.order().orderId())
//                .orElseThrow();


        Payment payment = Payment.builder()
                .id(paymentNotification.order().orderId())
                .date(paymentNotification.localReceiptDateTime())
                .payUPaymentId(paymentNotification.order().orderId())
                .status(paymentNotification.order().status())
                .amount(new BigDecimal(paymentNotification.order().totalAmount()))
                .currencyCode(paymentNotification.order().currencyCode())
                .payMethod(paymentNotification.order().payMethod().type())
                .build();

        paymentRepository.save(payment);

    }

    public Payment findById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow();
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public void deleteById(String id) {
        paymentRepository.deleteById(id);
    }

    public void deleteAll() {
        paymentRepository.deleteAll();
    }
}
