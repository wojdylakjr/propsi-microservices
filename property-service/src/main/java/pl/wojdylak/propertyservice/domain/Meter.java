package pl.wojdylak.propertyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "meter")
public class Meter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "meter_type")
    private String meterType;

    @JsonIgnoreProperties("meter")
    @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MeterMeasurement> meterMeasurements = new HashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "premises_id")
    private Premises premises;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Meter)) {
            return false;
        }
        return id != null && id.equals(((Meter) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}