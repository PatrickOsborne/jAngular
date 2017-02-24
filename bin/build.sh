PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done

# Get standard environment variables
PRGDIR=`dirname "$PRG"`

failed=0

# flags to control build, zero is false anything else will evaluate to true
skipTests=0

maven="mvn"
#maven="mvn -U"
mavenCmd="clean install"

if [ $skipTests -ne 0 ]; then
    mavenCmd="$mavenCmd -DskipTests"
fi

doMaven ()
{
    echo "calling maven cmd: " $maven $mavenCmd
    $maven $mavenCmd
    result=$?
    if [ $result -ne 0 ]; then
        echo "ERROR: maven call failed: " $result
        failed=1
    fi
}

# Only set MY_PROJ_HOME if not already set
[ -z "$MY_PROJ_HOME" ] && MY_PROJ_HOME=`cd "$PRGDIR/.." ; pwd`

echo MY_PROJ_HOME: $MY_PROJ_HOME

if [ $failed -eq 0 ]; then
    pushd $MY_PROJ_HOME
    doMaven
    popd
fi

if [ $failed -ne 0 ]; then
    echo "ERROR: build failed"
    exit 1
fi

exit 0