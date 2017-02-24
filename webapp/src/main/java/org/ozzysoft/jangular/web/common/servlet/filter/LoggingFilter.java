package org.ozzysoft.jangular.web.common.servlet.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingFilter implements Filter
{
    private static final Logger LOGGER = LoggerFactory.getLogger( LoggingFilter.class );

    @Override
    public void init( FilterConfig filterConfig ) throws ServletException
    {
        LOGGER.info( "init()" );
    }

    @Override
    public void destroy()
    {
        LOGGER.info( "destroy()" );
    }

    @Override
    public void doFilter( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain )
            throws IOException, ServletException
    {
        if ( !(servletRequest instanceof HttpServletRequest) )
        {
            LOGGER.warn( "not http servlet request" );
            runFilterChain( servletRequest, servletResponse, filterChain );
            return;
        }

        try
        {
            logRequest( (HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse );
        }
        finally
        {
            runFilterChain( servletRequest, wrapResponse( (HttpServletRequest) servletRequest, (HttpServletResponse) servletResponse ), filterChain );
        }
    }

    private void runFilterChain( ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain )
            throws IOException, ServletException
    {
        try
        {
            filterChain.doFilter( servletRequest, servletResponse );
        }
        catch ( Exception e )
        {
            LOGGER.info( "filter chain call failed", e );
            throw e;
        }
    }

    private void logRequest( HttpServletRequest request, HttpServletResponse servletResponse )
    {
        String queryString = request.getQueryString();
        String requestURI = request.getRequestURI();
        if ( requestURI.endsWith( ".png" ) || requestURI.endsWith( ".js" ) || requestURI.endsWith( ".css" ) || requestURI.endsWith( ".html" ) ||
                requestURI.endsWith( ".svg" ) || requestURI.endsWith( ".woff" ) )
        {
            return;
        }

        LOGGER.debug( "http request( {} ): remote: ({}) {}:{}, requestURI({}): {}{}", System.identityHashCode( request ), request.getRemoteAddr(),
                request.getRemoteHost(), request.getRemotePort(), request.getMethod(), request.getRequestURI(),
                queryString == null ? "" : "?" + queryString );
    }

    private ServletResponse wrapResponse( HttpServletRequest request, HttpServletResponse servletResponse )
    {
        return servletResponse;
    }

}
