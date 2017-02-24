package org.ozzysoft.jangular.common;

import static org.ozzysoft.jangular.common.util.ArgumentPreconditions.checkNotNullAndNotEmpty;

public class Version
{
    private final String version;
    private final String buildTimestamp;
    // git commit
    private final String commit;

    public Version( String version, String buildTimestamp, String commit )
    {
        String temp = checkNotNullAndNotEmpty( version, "version may not be null or empty!" ).trim();
        temp = temp.replace( "-SNAPSHOT", "" );
        this.version = temp;
        this.buildTimestamp = checkNotNullAndNotEmpty( buildTimestamp, "timestamp may not be null or empty!" ).trim();
        this.commit = checkNotNullAndNotEmpty( commit, "commit may not be null or empty!" ).trim();
    }

    public String getVersion()
    {
        return version;
    }

    public String getBuildTimestamp()
    {
        return buildTimestamp;
    }

    public String getCommit()
    {
        return commit;
    }

    @Override
    public String toString()
    {
        return "Version{ " +
                "version=" + version +
                ", timestamp='" + buildTimestamp + '\'' +
                ", commit=" + commit +
                " }";
    }

}
