package org.ozzysoft.jangular.web.common.servlet.filter;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseWrapper extends HttpServletResponseWrapper
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ResponseWrapper.class );

    public ResponseWrapper( HttpServletResponse response )
    {
        super( response );
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException
    {
        LOGGER.info( "getOutputStream()" );
        return new DelegatingServletOutputStream( new TeeOutputStream( super.getOutputStream(), new ByteArrayOutputStream() ) );
    }

    @Override
    public PrintWriter getWriter() throws IOException
    {
        LOGGER.info( "getWriter()" );
        return super.getWriter();
    }

    private class DelegatingServletOutputStream extends ServletOutputStream
    {
        private final ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        private final OutputStream delegate;

        private DelegatingServletOutputStream( OutputStream delegate )
        {
            checkNotNull( delegate, "delegate stream may not be null" );
            this.delegate = new TeeOutputStream( delegate, byteStream );
        }

        @Override
        public void write( int b ) throws IOException
        {
            delegate.write( b );
        }

        @Override
        public void flush() throws IOException
        {
            super.flush();
            delegate.flush();
        }

        @Override
        public void close() throws IOException
        {
            super.close();
            delegate.close();
            LOGGER.debug( "close(): {}", byteStream );
        }

    }

}
