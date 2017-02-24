package org.ozzysoft.jangular.web.common.services.security;

import static com.google.common.base.Preconditions.checkNotNull;

import org.springframework.security.core.GrantedAuthority;

public class GrantedAuthorityImpl implements GrantedAuthority, Comparable<GrantedAuthority>
{
    public enum Authority
    {
        USER(),
        ADMIN()
    }

    private final Authority authority;

    public GrantedAuthorityImpl( Authority authority )
    {
        this.authority = checkNotNull( authority );
    }

    @Override
    public String getAuthority()
    {
        return authority.name();
    }

    @Override
    public int compareTo( GrantedAuthority other )
    {
        return authority.name().compareTo( other.getAuthority() );
    }

    @Override
    public String toString()
    {
        return "GrantedAuthorityImpl{ " +
                "authority=" + authority +
                " }";
    }
}
