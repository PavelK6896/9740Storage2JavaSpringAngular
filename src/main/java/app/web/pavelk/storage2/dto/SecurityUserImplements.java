package app.web.pavelk.storage2.dto;

import app.web.pavelk.storage2.entities.Status;
import app.web.pavelk.storage2.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SecurityUserImplements implements UserDetails {

    private final String username;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;

    public SecurityUserImplements(String username,
                                  String password,
                                  List<SimpleGrantedAuthority> authorities,
                                  boolean isActive) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    public static UserDetails createUser(User user) {
        return new SecurityUserImplements(
                user.getUsername(), user.getPassword(),
                user.getRoles().stream().map(r -> {
                    return new SimpleGrantedAuthority(r.getName());
                }).collect(Collectors.toList()),
                user.getStatus().equals(Status.ACTIVE)
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
