package org.ozzysoft.jangular.web.common.services.security;

import com.google.common.collect.Sets;
import org.ozzysoft.jangular.common.util.GenericCollectionsHelper;
import org.ozzysoft.jangular.web.common.services.security.GrantedAuthorityImpl.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

import static org.ozzysoft.jangular.common.util.ArgumentPreconditions.checkNotNullAndNotEmpty;

public class UserDetailsImpl implements UserDetails {
    private final String username;
    private final String password;
    private final Set<GrantedAuthority> authorities = Sets.newHashSet();

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public UserDetailsImpl(String username, String password) {
        this(username, password, null, true, true, true, true);
    }

    public UserDetailsImpl(String username, String password, Set<GrantedAuthority> authorities) {
        this(username, password, authorities, true, true, true, true);
    }

    public UserDetailsImpl(String username, String password, Set<GrantedAuthority> authorities, boolean accountNonExpired, boolean accountNonLocked,
                           boolean credentialsNonExpired, boolean enabled) {
        this.username = checkNotNullAndNotEmpty(username);
        this.password = checkNotNullAndNotEmpty(password);
        if (authorities != null) {
            GenericCollectionsHelper.add(this.authorities, authorities);
        } else {
            this.authorities.add(new GrantedAuthorityImpl(Authority.USER));
        }
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserDetailsImpl{" +
                "username='" + username + '\'' +
                '}';
    }
}
