package org.ozzysoft.jangular.web.common.services.metrics;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Slf4jReporter.Builder;

@Service
public class MetricRegistryService
{
    private final MetricRegistry registry = new MetricRegistry();
    private final Slf4jReporter reporter;

    public MetricRegistryService()
    {
        reporter = createReporter();
    }

    @PostConstruct
    public void postConstruct()
    {
        reporter.start( 5, TimeUnit.MINUTES );
    }

    @PreDestroy
    public void preDestroy()
    {
        report();
        reporter.stop();
    }

    public MetricRegistry getRegistry()
    {
        return registry;
    }

    public void report()
    {
        reporter.report();
    }

    private Slf4jReporter createReporter()
    {
        Builder builder = Slf4jReporter.forRegistry( registry );
        builder.outputTo( LoggerFactory.getLogger( MetricRegistryService.class ) );
        builder.convertRatesTo( TimeUnit.MILLISECONDS );
        builder.convertDurationsTo( TimeUnit.MILLISECONDS );
        return builder.build();
    }
}
