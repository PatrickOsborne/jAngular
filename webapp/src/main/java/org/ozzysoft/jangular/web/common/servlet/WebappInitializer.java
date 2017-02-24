package org.ozzysoft.jangular.web.common.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

public class WebappInitializer extends AbstractSecurityWebApplicationInitializer
{
    private static final Logger LOGGER = LoggerFactory.getLogger( WebappInitializer.class );

    @Override
    protected void beforeSpringSecurityFilterChain( ServletContext servletContext )
    {
        LOGGER.trace( "beforeSpringSecurityFilterChain()" );
        super.beforeSpringSecurityFilterChain( servletContext );
    }

    @Override
    protected void afterSpringSecurityFilterChain( ServletContext servletContext )
    {
        LOGGER.trace( "afterSpringSecurityFilterChain()" );
        super.afterSpringSecurityFilterChain( servletContext );

        ServletRegistration.Dynamic restServlet = servletContext.addServlet( "rest", new DispatcherServlet() );
        restServlet.setLoadOnStartup( 1 );
        restServlet.addMapping( "/rest/*" );
    }

}