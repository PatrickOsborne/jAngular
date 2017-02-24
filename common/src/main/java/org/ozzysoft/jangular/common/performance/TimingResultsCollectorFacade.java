package org.ozzysoft.jangular.common.performance;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class TimingResultsCollectorFacade implements TimingResultsCollector
{
    private static final Logger LOGGER = LoggerFactory.getLogger( TimingResultsCollectorFacade.class );

    private final List<TimingResultsCollector> collectors = Lists.newArrayList();
    private final AtomicInteger reportingFrequency = new AtomicInteger( 100 );

    public TimingResultsCollectorFacade( TimingResultsCollector... collectors )
    {
        for ( int i = 0; i < collectors.length; i++ )
        {
            TimingResultsCollector collector = collectors[i];
            if ( i == 0 )
            {
                checkNotNull( collector );
            }

            if ( collector != null )
            {
                this.collectors.add( collector );
            }
        }
    }

    @Override
    public String getName()
    {
        return getMainCollector().getName();
    }

    @Override
    public TimingResults getResults()
    {
        return getMainCollector().getResults();
    }

    @Override
    public TimingResults addOp( double opValue )
    {
        TimingResults firstResults = null;
        for ( int i = 0; i < collectors.size(); i++ )
        {
            TimingResultsCollector collector = collectors.get( i );
            if ( i == 0 )
            {
                firstResults = collector.addOp( opValue );
            }
            else
            {
                collector.addOp( opValue );
            }
        }

        if ( firstResults.opCountHasModulusZero( reportingFrequency.get() ) )
        {
            LOGGER.info( "intermediate timing results: {} -> {}", firstResults.getName(), firstResults );
        }

        return firstResults;
    }

    public List<TimingResultsCollector> getCollectors()
    {
        return collectors;
    }

    public int getReportingFrequency()
    {
        return reportingFrequency.get();
    }

    public TimingResultsCollectorFacade setReportingFrequency( int reportingFrequency )
    {
        this.reportingFrequency.set( reportingFrequency );
        return this;
    }

    private TimingResultsCollector getMainCollector()
    {
        return collectors.get( 0 );
    }
}
