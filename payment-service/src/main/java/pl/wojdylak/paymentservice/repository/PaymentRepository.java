package pl.wojdylak.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wojdylak.paymentservice.domain.Payment;

public interface PaymentRepository extends JpaRepository<Payment, String> {
}
