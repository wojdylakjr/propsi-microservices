package pl.wojdylak.propertyservice.domain;


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
@Table(name = "property")
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "single_premises")
    private Boolean isSinglePremises;

    @Column(name = "owner_id")
    private Long ownerId;


    @JsonIgnoreProperties(value = "property", allowSetters = true)
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "property", fetch = FetchType.LAZY)
    private Address address;

    @JsonIgnoreProperties(value = "property", allowSetters = true)
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private Set<PropertyFixedCost> fixedCosts = new HashSet<>();

    @JsonIgnoreProperties(value = "property", allowSetters = true)
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private Set<Premises> premises = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        return id != null && id.equals(((Property) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
