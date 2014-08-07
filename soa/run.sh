#!/bin/bash

BASEDIR=$PWD
SCRIPTS_PATH=$BASEDIR/scripts
CURL_APP='http://localhost:8080/ode/processes/BestDogMainProcess?wsdl'

. env.sh

TERMINAL_CMD="${1-xfce4-terminal --tab --title V_TITLE --drop-down -e}"

open_tab() {
  CMD="bash -c '. env.sh && V_CMD; bash'"
  CMD_PARSED=`echo $CMD | sed "s#V_CMD#$2/$1#"`
  TERMINAL_CMD_PARSED=`echo $TERMINAL_CMD | sed "s/V_TITLE/$1/"`

  echo "[$TERMINAL_CMD \"$CMD_PARSED\"]"
  
  $TERMINAL_CMD_PARSED "$CMD_PARSED" &
}

if [ $? -ne 0 ]; then
	echo ""
	echo "ERROR: Maven not found."
	exit -1
fi

curl -IL $CURL_APP &> /tmp/_curl
grep "HTTP/1.1 200" /tmp/_curl

if [ $? -eq 0 ]; then
 echo "already started"
else
 psjava=`ps aux | grep java | grep _ode | grep -v grep | wc -l`
 if [ $psjava -eq 1 ]; then
 	echo "already running"
 else
	cd $BASEDIR/bpel
	mvn package -DskipTests -Dmaven.test.skip=true
	cd $BASEDIR

	cd $BASEDIR/ws/bestdog
	mvn package -DskipTests -Dmaven.test.skip=true
	cd $BASEDIR

	echo ""

	echo "STARTING H2..."
	open_tab h2 $SCRIPTS_PATH

	echo "STARTING WS..."
	open_tab ws $SCRIPTS_PATH

	echo "STARTING ODE..."
	open_tab ode $SCRIPTS_PATH

	echo "aguarde 1 minuto..."
	sleep 60

	curl -IL $CURL_APP &> /tmp/_curl

	if [ $? -ne 0 ]; then
		    echo "..."
	        echo "aguarde 30s..."
        	sleep 30
	fi

	echo "deploying..."
	cd bpel
	./bpel.sh
	cd ..

 fi
fi

exo-open start.html
