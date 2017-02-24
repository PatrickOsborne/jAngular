package org.ozzysoft.jangular.web.common.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ozzysoft.jangular.web.common.services.security.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private static final Logger LOGGER = LoggerFactory.getLogger( SecurityConfig.class );

    @Bean
    public InternalResourceViewResolver viewResolver()
    {
        LOGGER.info( "viewResolver()" );

        InternalResourceViewResolver result = new InternalResourceViewResolver();
        result.setPrefix( "/" );
        result.setSuffix( ".jsp" );
        return result;
    }

    @Autowired
    public void configureGlobal( AuthenticationManagerBuilder authBuilder ) throws Exception
    {
        LOGGER.debug( "configureGlobal()" );
        DaoAuthenticationConfigurer<AuthenticationManagerBuilder, UserDetailsServiceImpl> authConfigurer = authBuilder.userDetailsService(
                new UserDetailsServiceImpl() );
        authConfigurer.passwordEncoder( new BCryptPasswordEncoder() );
    }

    @Override
    protected void configure( HttpSecurity httpSecurity ) throws Exception
    {
        LOGGER.debug( "configure()" );

        FormLoginConfigurer<HttpSecurity> formLoginConfigurer = httpSecurity.formLogin();
        formLoginConfigurer.loginPage( "/unsecure/login.jsp" ).permitAll();
        formLoginConfigurer.loginProcessingUrl( "/login" );
        formLoginConfigurer.usernameParameter( "username" );
        formLoginConfigurer.passwordParameter( "password" );

        LogoutConfigurer<HttpSecurity> logoutConfigurer = httpSecurity.logout();
        logoutConfigurer.logoutUrl( "/logout" );
        logoutConfigurer.logoutSuccessHandler( new LogoutSuccessHandlerImpl() );

        // unsecured UI paths: login js and third-part js libs
        httpSecurity.authorizeRequests().antMatchers( "/unsecure/**" ).permitAll();
        httpSecurity.authorizeRequests().antMatchers( "/lib/**" ).permitAll();

        httpSecurity.authorizeRequests().anyRequest().authenticated();
    }

    private class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
    {
        @Override
        public void onLogoutSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication )
                throws IOException, ServletException
        {
            // override default redirect behavior and just return status, client will do redirect to login page.
            LOGGER.info( "onLogoutSuccess(): {}", authentication );
            response.setStatus( 202 );
        }
    }
}