package pl.wojdylak.propertyservice.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "property_fixed_cost")
public class PropertyFixedCost implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    //TODO: Change to @Enumerated ?
    @Column(name = "cost_type")
    private String costType;

    @Column(name = "cost_value")
    private BigDecimal costValue;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private Property property;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PropertyFixedCost)) {
            return false;
        }
        return id != null && id.equals(((PropertyFixedCost) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}