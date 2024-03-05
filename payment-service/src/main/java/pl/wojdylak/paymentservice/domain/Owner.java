package pl.wojdylak.paymentservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "owner")
public class Owner implements Serializable {

    @Id
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
    private Long payUAccessTokenExpiration;
}