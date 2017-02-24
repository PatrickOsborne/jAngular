package org.ozzysoft.jangular.web.rest.basic;

import static com.codahale.metrics.MetricRegistry.name;
import static com.google.common.base.Preconditions.checkNotNull;

import org.ozzysoft.jangular.common.Version;
import org.ozzysoft.jangular.web.common.VersionService;
import org.ozzysoft.jangular.web.common.services.metrics.MetricRegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

@Controller
public class CommonController
{
    private static final Logger LOGGER = LoggerFactory.getLogger( CommonController.class );

    private final MetricRegistryService metricService;
    private final VersionService versionService;
    private final Timer versionTimer;

    @Autowired
    public CommonController( MetricRegistryService metricService, VersionService versionService )
    {
        this.metricService = checkNotNull( metricService );
        this.versionService = checkNotNull( versionService );

        versionTimer = metricService.getRegistry().timer( name( CommonController.class, "version" ) );
    }

    @RequestMapping(value = "/version", method = RequestMethod.POST)
    public
    @ResponseBody
    Version version()
    {
        LOGGER.trace( "version()" );
        Context timingContext = versionTimer.time();
        Version version = versionService.getVersion();
        timingContext.stop();
        return version;
    }
}
