package pl.wojdylak.propertyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "premises_cost_detail")
public class PremisesCostDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cost_value")
    private BigDecimal costValue;

    @Column(name = "date")
    private Instant date;

    @Column(name = "unit")
    private String unit;

    //TODO: check fetchType
    @JsonIgnoreProperties("premisesCostDetails")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "premises_cost_id")
    private PremisesCost premisesCost;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremisesCostDetail)) {
            return false;
        }
        return id != null && id.equals(((PremisesCostDetail) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
