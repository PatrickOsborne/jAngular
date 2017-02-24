package org.ozzysoft.jangular.web.rest.basic;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GreetingController
{
    private static final Logger LOGGER = LoggerFactory.getLogger( GreetingController.class );

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public GreetingController()
    {
        LOGGER.info( "created" );
    }

    @RequestMapping("/greeting")
    public
    @ResponseBody
    Greeting greeting( @RequestParam(value = "name", required = false, defaultValue = "World") String name )
    {
        LOGGER.debug( "greeting(): name: {}", name );
        return new Greeting( counter.incrementAndGet(), String.format( template, name ) );
    }
}
