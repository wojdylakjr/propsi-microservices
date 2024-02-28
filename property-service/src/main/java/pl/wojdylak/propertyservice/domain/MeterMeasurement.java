package pl.wojdylak.propertyservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "meter_measurement")
public class MeterMeasurement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measurement_value")
    private Double value;

    @Column(name = "measurement_date")
    private Instant date;

    @Column(name = "unit")
    private String unit;

    @JsonIgnoreProperties("meterMeasurements")
    //TODO: lazy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meter_id")
    private Meter meter;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MeterMeasurement)) {
            return false;
        }
        return id != null && id.equals(((MeterMeasurement) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "MeterMeasurement{" +
                "id=" + id +
                ", value=" + value +
                ", date=" + date +
                ", unit='" + unit + '\'' +
                ", meter=" + meter.getId() +
                '}';
    }
}
