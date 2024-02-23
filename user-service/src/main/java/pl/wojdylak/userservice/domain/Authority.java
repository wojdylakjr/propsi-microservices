package pl.wojdylak.userservice.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "AUTHORITY")
public class Authority {
    @Id
    @Enumerated(EnumType.STRING)
    private AuthorityEnum name;

    public Authority(String authority) {
        this.name = AuthorityEnum.valueOf(authority);
    }

}
