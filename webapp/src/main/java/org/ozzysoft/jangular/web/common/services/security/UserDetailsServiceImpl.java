package org.ozzysoft.jangular.web.common.services.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Simple implementation which uses username as the password.
 * <p/>
 * This class is useful to get a project started but should be replaced with a service that uses an appropriate persistence mechanism.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger( UserDetailsServiceImpl.class );

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException
    {
        LOGGER.info( "loadUserByUsername(): {}", username );
        return new UserDetailsImpl( username, passwordEncoder.encode( username ) );
    }
}
