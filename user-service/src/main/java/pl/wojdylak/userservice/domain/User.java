package pl.wojdylak.userservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "MY-USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;


    @ManyToMany
    @JoinTable(
            name = "rel_user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")}
    )
    private Set<Authority> authorities = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "rel_user_owner",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "owner_id")
    )
    private Set<Owner> owners = new HashSet<>();


    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "rel_user_tenant",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tenant_id")
    )
    private Set<Tenant> tenants = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}