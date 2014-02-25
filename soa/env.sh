#!/bin/bash

export JAVA_HOME=/usr
export JAVA_VERSION=java-7-oracle
export JAVA_DIR=/usr/lib/jvm
export JAVA_HOME=$JAVA_DIR
export MVN_DIR=/home/soa/Apps/apache-maven-3.1.1
export J2SDKDIR=$JAVA_HOME/$JAVA_VERSION
export J2REDIR=$J2SDKDIR/jre
export JAVA_HOME=$J2SDKDIR
export DERBY_HOME=$J2SDKDIR/db
export JAVA_XOPTS="-Duser.timezone=GMT -Djava.security.policy=~/.java.policy"
export JAVA_OPTS="-XX:MaxPermSize=400m -Xms256m -Xmx768m -XX:PermSize=128m -server $JAVA_XOPTS"
export MAVEN_OPTS="-XX:MaxPermSize=400m -Xmx512m"
export MAVEN_OPTS_DEBUG="$MAVEN_OPTS -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=8000"

export PATH=${PATH}:$JAVA_HOME/bin:$DERBY_HOME/bin:$J2SDKDIR/jre/bin:$MVN_HOME/bin

