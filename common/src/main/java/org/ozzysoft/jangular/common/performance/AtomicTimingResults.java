package org.ozzysoft.jangular.common.performance;

import java.text.NumberFormat;
import java.util.concurrent.atomic.AtomicReference;

import org.ozzysoft.jangular.common.util.Named;
import org.ozzysoft.jangular.common.util.NamedImpl;

/**
 * Thread safe timer results object.
 */
public class AtomicTimingResults implements TimingResultsCollector
{
    private static final String EMPTY_STRING = "";

    private final Named id;
    private final String description;

    private final AtomicReference<ImmutableTimingResults> resultsRef;

    public AtomicTimingResults( final String name )
    {
        this( new NamedImpl( name ), EMPTY_STRING );
    }

    public AtomicTimingResults( final Named id )
    {
        this( id, EMPTY_STRING );
    }

    public AtomicTimingResults( final Named id, final String description )
    {
        this.id = verifiedNotNull( id );
        resultsRef = new AtomicReference<ImmutableTimingResults>( new ImmutableTimingResults( id ) );
        this.description = description;
    }

    public Named getId()
    {
        return id;
    }

    public String getName()
    {
        return id.getName();
    }

    public String getDescription()
    {
        return description;
    }

    @Override
    public TimingResults addOp( final double opValue )
    {
        while ( true )
        {
            final ImmutableTimingResults results = getResultsInternal();
            final ImmutableTimingResults newResults = ImmutableTimingResults.createInstance( opValue, results );
            if ( resultsRef.compareAndSet( results, newResults ) )
            {
                // results update successful
                return newResults;
            }
            // try again.
        }
    }

    @Override
    public TimingResults getResults()
    {
        return getResultsInternal();
    }

    private ImmutableTimingResults getResultsInternal()
    {
        return resultsRef.get();
    }

    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( o == null || getClass() != o.getClass() )
        {
            return false;
        }

        final AtomicTimingResults that = (AtomicTimingResults) o;
        return id.equals( that.id );
    }

    public int hashCode()
    {
        return id.hashCode();
    }

    private static <T> T verifiedNotNull( final T argument )
    {
        if ( argument == null )
        {
            throw new NullPointerException();
        }
        return argument;
    }


    /**
     * Immutable.
     */
    private static class ImmutableTimingResults implements TimingResults, Named
    {
        private final Named id;
        private final boolean derivedValuesSet;

        private final long opCount;
        private final double total;

        private final double min;
        private final double max;
        private final double first;
        private final double last;

        public static ImmutableTimingResults createInstance( final double opValue, final ImmutableTimingResults previous )
        {
            verifiedNotNull( previous );
            verifiedPositive( opValue );

            final ImmutableTimingResults results;
            if ( !previous.derivedValuesSet )
            {
                results = new ImmutableTimingResults( previous.id, opValue );
            }
            else
            {
                results = new ImmutableTimingResults( previous.id, previous.opCount + 1, previous.total + opValue, Math.min( opValue, previous.min ),
                        Math.max( opValue, previous.max ), previous.first, opValue );
            }

            return results;
        }

        public ImmutableTimingResults( final Named id )
        {
            this.id = verifiedNotNull( id );
            this.derivedValuesSet = false;

            this.opCount = 0;
            this.total = 0.0d;

            this.min = 0.0d;
            this.max = 0.0d;
            this.first = 0.0d;
            this.last = 0.0d;
        }

        private ImmutableTimingResults( final Named id, final double value )
        {
            this( id, 1, value, value, value, value, value );
        }

        private ImmutableTimingResults( final Named id, final long opCount, final double total, final double min, final double max,
                final double first, final double last )
        {
            this.id = verifiedNotNull( id );
            this.derivedValuesSet = true;

            this.opCount = opCount;
            this.total = total;

            this.min = min;
            this.max = max;
            this.first = first;
            this.last = last;
        }

        public String getName()
        {
            return id.getName();
        }

        public long opCount()
        {
            return opCount;
        }

        public boolean opCountHasModulusZero( final long value )
        {
            return (value == 0) ? false : (opCount() % value) == 0;
        }

        public String getOpCount()
        {
            return getOpCount( null );
        }

        public String getOpCount( final NumberFormat format )
        {
            return format( opCount, format );
        }

        public double total()
        {
            return total;
        }

        public String getTotal()
        {
            return getTotal( null );
        }

        public String getTotal( final NumberFormat format )
        {
            return format( total, format );
        }

        public double min()
        {
            return min;
        }

        public String getMin()
        {
            return getMin( null );
        }

        public String getMin( final NumberFormat format )
        {
            return format( min, format );
        }

        public double max()
        {
            return max;
        }

        public String getMax()
        {
            return getMax( null );
        }

        public String getMax( final NumberFormat format )
        {
            return format( max, format );
        }

        public double first()
        {
            return first;
        }

        public String getFirst()
        {
            return getFirst( null );
        }

        public String getFirst( final NumberFormat format )
        {
            return format( first, format );
        }

        public double last()
        {
            return last;
        }

        public String getLast()
        {
            return getLast( null );
        }

        public String getLast( final NumberFormat format )
        {
            return format( last, format );
        }

        public double avg()
        {
            if ( opCount == 0 )
            {
                return total;
            }
            return total / opCount;
        }

        public String getAvg()
        {
            return getAvg( null );
        }

        public String getAvg( final NumberFormat format )
        {
            return format( avg(), format );
        }

        private static <T extends Number> String format( final T value, final NumberFormat format )
        {
            if ( format == null )
            {
                return value.toString();
            }
            return format.format( value );
        }

        private static double verifiedPositive( final double argument )
        {
            if ( Double.compare( argument, 0 ) < 0.0d )
            {
                throw new IllegalArgumentException( "value must be positive: " + argument );
            }
            return argument;
        }

        public String toString()
        {
            return toString( null );
        }

        public String toString( final NumberFormat format )
        {
            final StringBuilder sb = new StringBuilder( 256 );
            sb.append( "Timer Results(" + id + ")[ " );

            sb.append( "op count( " + getOpCount( format ) + " )" );
            sb.append( ", total( " + getTotal( format ) + " )" );
            sb.append( ", avg( " + getAvg( format ) + " )" );
            sb.append( ", min( " + getMin( format ) + " )" );
            sb.append( ", max( " + getMax( format ) + " )" );
            sb.append( ", first( " + getFirst( format ) + " )" );
            sb.append( ", last( " + getLast( format ) + " )" );

            sb.append( " ]" );
            return sb.toString();
        }

    }
}
