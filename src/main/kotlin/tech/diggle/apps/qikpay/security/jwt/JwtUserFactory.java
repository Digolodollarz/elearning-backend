package tech.diggle.apps.qikpay.security.jwt;


import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tech.diggle.apps.qikpay.security.authority.Authority;
import tech.diggle.apps.qikpay.security.authority.AuthorityName;
import tech.diggle.apps.qikpay.security.user.User;

//@Service
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
                .collect(Collectors.toList());
    }

    private static List<Authority> mapToAuthorities(Collection<GrantedAuthority> authorities) {
        return authorities.stream()
                .map(authority -> {
                    Authority auth = new Authority();
                    auth.setName(AuthorityName.valueOf(authority.getAuthority()));
                    return auth;
                })
                .collect(Collectors.toList());
    }

    public static User user(JwtUser user) {
        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setFirstname(user.getFirstname());
        usr.setLastname(user.getLastname());
        usr.setEmail(user.getEmail());
        usr.setPassword(user.getPassword());
        usr.setAuthorities(mapToAuthorities(user.getAuthorities()));
        usr.setEnabled(user.isEnabled());
        usr.setLastPasswordResetDate(usr.getLastPasswordResetDate());
        return usr;
    }
}

