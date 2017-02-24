package org.ozzysoft.jangular.common.performance;

import java.text.NumberFormat;

import org.ozzysoft.jangular.common.util.Named;

public interface TimingResults extends Named
{
    long opCount();

    String getOpCount();

    boolean opCountHasModulusZero( long value );

    String getOpCount( NumberFormat format );

    double total();

    String getTotal();

    String getTotal( NumberFormat format );

    double min();

    String getMin();

    String getMin( NumberFormat format );

    double max();

    String getMax();

    String getMax( NumberFormat format );

    double first();

    String getFirst();

    String getFirst( NumberFormat format );

    double last();

    String getLast();

    String getLast( NumberFormat format );

    double avg();

    String getAvg();

    String getAvg( NumberFormat format );

    String toString( NumberFormat format );

}
