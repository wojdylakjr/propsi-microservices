package pl.wojdylak.propertyservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "premises")
public class Premises implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    //TODO: to lazy
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "property_id")
    private Property property;

    @OneToMany(mappedBy = "premises", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PremisesCost> premisesCosts = new HashSet<>();

    @OneToMany(mappedBy = "premises", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Meter> meters = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Premises)) {
            return false;
        }
        return id != null && id.equals(((Premises) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
