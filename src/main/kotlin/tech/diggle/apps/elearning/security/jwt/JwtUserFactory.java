package tech.diggle.apps.elearning.security.jwt;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import tech.diggle.apps.elearning.security.authority.Authority;
import tech.diggle.apps.elearning.security.authority.AuthorityName;
import tech.diggle.apps.elearning.security.user.User;

//@Service
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getAuthorities()),
                user.getEnabled(),
                user.getLastPasswordResetDate()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        authorities.forEach(
                authority -> {
                    if(authority != null && authority.getName() != null){
                        grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName().name()));
                    }
                }
        );

        return grantedAuthorities;
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
        usr.setFirstName(user.getFirstname());
        usr.setLastName(user.getLastname());
        usr.setEmail(user.getEmail());
        usr.setPassword(user.getPassword());
        usr.setAuthorities(mapToAuthorities(user.getAuthorities()));
        usr.setEnabled(user.isEnabled());
        usr.setLastPasswordResetDate(usr.getLastPasswordResetDate());
        return usr;
    }
}

