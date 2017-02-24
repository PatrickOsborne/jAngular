package org.ozzysoft.jangular.common.util;

import static org.ozzysoft.jangular.common.util.ArgumentPreconditions.checkNotNullAndNotEmpty;

public class NamedImpl implements Named
{
    private final String name;

    public NamedImpl( String name )
    {
        this.name = checkNotNullAndNotEmpty( name );
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return "Named{ " + name + " }";
    }
}
