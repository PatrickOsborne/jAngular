package org.ozzysoft.jangular.web.common;

import org.ozzysoft.jangular.common.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class VersionService
{
    private final Version version;

    @Autowired
    public VersionService( @Qualifier("versionProperties") Properties properties )
    {
        this.version = new Version( properties.getProperty( "jAngular.build.version" ), properties.getProperty( "jAngular.build.timestamp" ),
                properties.getProperty( "jAngular.build.commit" ) );
    }

    public Version getVersion()
    {
        return version;
    }

    @Override
    public String toString()
    {
        return "VersionService{ " +
                "version=" + version +
                " }";
    }

}
