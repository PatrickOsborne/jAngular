package org.ozzysoft.jangular.common.util;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;

public class Duration implements Comparable<Duration>
{
    private static final DurationComparator DURATION_COMPARATOR = new DurationComparator();

    private final long duration;
    private final TimeUnit unit;

    public Duration( final long duration, final TimeUnit unit )
    {
        this.duration = duration;
        this.unit = unit;
    }

    public long getDuration()
    {
        return duration;
    }

    public TimeUnit getUnit()
    {
        return unit;
    }

    @Override
    public int compareTo( final Duration other )
    {
        Preconditions.checkNotNull( other );
        return DURATION_COMPARATOR.compare( this, other );
    }

    public boolean isGreaterThan( final Duration duration )
    {
        return compareTo( duration ) >= 1;
    }

    public boolean isLessThan( final Duration duration )
    {
        return compareTo( duration ) <= -1;
    }

    @Override
    public boolean equals( final Object other )
    {
        if ( this == other )
        {
            return true;
        }
        if ( other == null || getClass() != other.getClass() )
        {
            return false;
        }

        final Duration otherDuration = (Duration) other;
        return compareTo( otherDuration ) == 0;
    }

    @Override
    public int hashCode()
    {
        long convertedDuration = convert( this, TimeUnit.NANOSECONDS );
        int result = (int) (convertedDuration ^ convertedDuration >>> 32);
        result = 31 * result + TimeUnit.NANOSECONDS.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return "Duration{ " + duration + " " + unit + " }";
    }

    public static long convert( final Duration duration, final TimeUnit convertToUnit )
    {
        return convert( duration.getDuration(), duration.getUnit(), convertToUnit );
    }

    public static long convert( final long duration, final TimeUnit unit, final TimeUnit convertToUnit )
    {
        return convertToUnit.convert( duration, unit );
    }

    public static class DurationComparator implements Comparator<Duration>
    {
        private static final UnitComparator UNIT_COMPARATOR = new UnitComparator();
        private static final LongComparator LONG_COMPARATOR = new LongComparator();

        @Override
        public int compare( final Duration durationLeft, final Duration durationRight )
        {
            int result = UNIT_COMPARATOR.compare( durationLeft.getUnit(), durationRight.getUnit() );
            // convert larger unit to smaller unit then compare values
            switch ( result )
            {
                case 0:
                    return LONG_COMPARATOR.compare( durationLeft.getDuration(), durationRight.getDuration() );
                case 1:
                    return LONG_COMPARATOR.compare( convert( durationLeft, durationRight.getUnit() ), durationRight.getDuration() );
                case -1:
                    return LONG_COMPARATOR.compare( durationLeft.getDuration(), convert( durationRight, durationLeft.getUnit() ) );
                default:
                    throw new IllegalStateException( "invalid unit compare result: " + result );
            }
        }
    }

    public static class LongComparator implements Comparator<Long>
    {
        @Override
        public int compare( final Long left, final Long right )
        {
            return left.compareTo( right );
        }
    }

    public static class UnitComparator implements Comparator<TimeUnit>
    {
        @Override
        public int compare( final TimeUnit unitLeft, final TimeUnit unitRight )
        {
            switch ( unitLeft )
            {
                case NANOSECONDS:
                    switch ( unitRight )
                    {
                        case NANOSECONDS:
                            return 0;
                        default:
                            return -1;
                    }
                case MICROSECONDS:
                    switch ( unitRight )
                    {
                        case MICROSECONDS:
                            return 0;
                        case NANOSECONDS:
                            return 1;
                        default:
                            return -1;
                    }
                case MILLISECONDS:
                    switch ( unitRight )
                    {
                        case MILLISECONDS:
                            return 0;
                        case MICROSECONDS:
                        case NANOSECONDS:
                            return 1;
                        default:
                            return -1;
                    }
                case SECONDS:
                    switch ( unitRight )
                    {
                        case SECONDS:
                            return 0;
                        case MILLISECONDS:
                        case MICROSECONDS:
                        case NANOSECONDS:
                            return 1;
                        default:
                            return -1;
                    }
                case MINUTES:
                    switch ( unitRight )
                    {
                        case MINUTES:
                            return 0;
                        case SECONDS:
                        case MILLISECONDS:
                        case MICROSECONDS:
                        case NANOSECONDS:
                            return 1;
                        default:
                            return -1;
                    }
                case HOURS:
                    switch ( unitRight )
                    {
                        case HOURS:
                            return 0;
                        case MINUTES:
                        case SECONDS:
                        case MILLISECONDS:
                        case MICROSECONDS:
                        case NANOSECONDS:
                            return 1;
                        default:
                            return -1;
                    }
                case DAYS:
                    switch ( unitRight )
                    {
                        case DAYS:
                            return 0;
                        default:
                            return 1;
                    }
                default:
                    throw new IllegalArgumentException( "unexpected left time unit " + unitLeft );
            }
        }
    }
}

