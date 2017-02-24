package org.ozzysoft.jangular.common.util;

import java.util.Set;

public class GenericCollectionsHelper
{
    public static <T> void add( Set<T> set, Iterable<T> iterable )
    {
        for ( T value : iterable )
        {
            set.add( value );
        }
    }
}
