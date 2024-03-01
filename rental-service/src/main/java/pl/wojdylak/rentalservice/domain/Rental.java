package pl.wojdylak.rentalservice.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(name = "rental")
public class Rental implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Column(name = "owner_id")
    private Long ownerId;

    @Column(name = "premises_id")
    private Long premisesId;

    @Column(name = "name")
    private String name;

    @Column(name = "rent_price")
    private BigDecimal rentPrice;

    @Column(name = "rental_start_date")
    private Instant rentalStartDate;

    @Column(name = "rental_end_date")
    private Instant rentalEndDate;

    @Column(name = "payment_day")
    private Integer paymentDay;

    @Column(name = "costs_part")
    private Double costsPart;


    @JsonIgnoreProperties("rental")
    @OneToMany(mappedBy = "rental", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bill> bills = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rental)) {
            return false;
        }
        return id != null && id.equals(((Rental) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
