#!/bin/bash
# BEGIN_CONF
version="${1:-java-7-oracle}"
jdk_dir="${2:-/usr/lib/jvm}"
mvn_dir="$3"
# END_CONF

echo $version
echo $jdk_dir
echo $mvn_dir

export J2SDKDIR="$jdk_dir/$version"
export MVN_HOME="$mvn_dir"

export J2REDIR=$J2SDKDIR/jre
export JAVA_HOME=$J2SDKDIR
export DERBY_HOME=$J2SDKDIR/db

export JAVA_XOPTS="-Duser.timezone=GMT -Djava.security.policy=~/.java.policy"
export JAVA_OPTS="-XX:MaxPermSize=400m -Xms256m -Xmx768m -XX:PermSize=128m -server $JAVA_XOPTS"

export MAVEN_OPTS="-XX:MaxPermSize=400m -Xmx512m"
export MAVEN_OPTS_DEBUG="$MAVEN_OPTS -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

export PATH=$JAVA_HOME/bin:$DERBY_HOME/bin:$J2SDKDIR/jre/bin:$MVN_HOME/bin:$PATH

mvn -version