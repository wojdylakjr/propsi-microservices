package pl.wojdylak.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorityEnum {
    ROLE_USER("ROLE_USER"),
    ROLE_OWNER("ROLE_OWNER"),
    ROLE_TENANT("ROLE_TENANT"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String value;
}
