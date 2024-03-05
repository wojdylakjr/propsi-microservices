package pl.wojdylak.paymentservice.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment")
public class Payment implements Serializable {

    @Id
    private String id;

    @Column(name = "bill_id")
    private Long billId;

    @Column(name = "date")
    private Date date;

    @Column(name = "payU_payment_id")
    private String payUPaymentId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "pay_method")
    private String payMethod;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "status")
    private String status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
