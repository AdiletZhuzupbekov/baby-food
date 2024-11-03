package kg.mara.babyfood.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN, USER, DRIVER, SUPER_ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
