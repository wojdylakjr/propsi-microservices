package pl.wojdylak.userservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "owner")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "payU_client_id")
    private String payUClientId;

    @Column(name = "payU_client_secret")
    private String payUClientSecret;

    @JsonIgnore
    @Column(name = "payU_access_token")
    private String payUAccessToken;

    @JsonIgnore
    @Column(name = "payU_access_token_expiration")
    private Instant payUAccessTokenExpiration;
    @JsonIgnore
    @ManyToMany(mappedBy = "owners")
    private Set<User> users = new HashSet<>();

    public Owner(String name) {
        this.name = name;
    }

}