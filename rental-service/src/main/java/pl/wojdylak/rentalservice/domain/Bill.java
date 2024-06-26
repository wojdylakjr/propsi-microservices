package pl.wojdylak.rentalservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bill")
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "date")
    private Instant date;


    //TODO: lazy
    @JsonIgnoreProperties({"bills"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rental_id")
    private Rental rental;

    @JsonIgnoreProperties("bill")
    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    private Set<BillLineItem> billLineItems = new HashSet<>();

    public Bill(Instant date, Rental rental) {
        this.date = date;
        this.rental = rental;
    }

    public void addBillLineItems(Set<BillLineItem> billLineItems) {
        this.billLineItems = billLineItems;
        this.totalPrice = billLineItems.stream()
                .map(BillLineItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bill)) {
            return false;
        }
        return id != null && id.equals(((Bill) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
