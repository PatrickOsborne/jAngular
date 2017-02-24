package org.ozzysoft.jangular.common.performance;

import org.ozzysoft.jangular.common.util.Named;

public interface TimingResultsCollector extends Named
{
    TimingResults addOp( double opValue );

    TimingResults getResults();
}
