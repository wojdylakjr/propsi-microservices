package pl.wojdylak.propertyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "premises_cost")
public class PremisesCost implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    back to enum?
    @Column(name = "cost_type")
    private String costType;

    //TODO: check it
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "premises_id")
    private Premises premises;

    @JsonIgnoreProperties("premisesCost")
    @OneToMany(mappedBy = "premisesCost", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PremisesCostDetail> premisesCostDetails = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PremisesCost)) {
            return false;
        }
        return id != null && id.equals(((PremisesCost) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
