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

        return payUClient.addOrder(payUOrderRequestCreator.create(bill, ownerDto),
                payUTokenResponse.accessToken());

    }

    public void savePaymentResponse(PayUPaymentNotification paymentNotification) {

        Payment payment = Payment.builder()
                .id(paymentNotification.order().extOrderId())
                .date(paymentNotification.localReceiptDateTime())
                .payUPaymentId(paymentNotification.order().orderId())
                .status(paymentNotification.order().status())
                .amount(new BigDecimal(paymentNotification.order().totalAmount()))
                .currencyCode(paymentNotification.order().currencyCode())
                .payMethod(paymentNotification.order().payMethod().type())
                .build();

        paymentRepository.save(payment);

    }
}
