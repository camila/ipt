#!/bin/bash

BASEDIR=$PWD
SCRIPTS_PATH=$BASEDIR/scripts

. env.sh

TERMINAL_CMD="${1-xfce4-terminal --tab --title V_TITLE --drop-down -e}"

open_tab() {
  CMD="bash -c '. env.sh && V_CMD; bash'"
  CMD_PARSED=`echo $CMD | sed "s#V_CMD#$2/$1#"`
  TERMINAL_CMD_PARSED=`echo $TERMINAL_CMD | sed "s/V_TITLE/$1/"`

  echo "[$TERMINAL_CMD \"$CMD_PARSED\"]"
  #read -p "(press ENTER to confirm)"
  $TERMINAL_CMD_PARSED "$CMD_PARSED"
}

if [ $? -ne 0 ]; then
	echo ""
	echo "ERROR: Maven not found."
	exit -1
fi

cd $BASEDIR/bpel
$MVN_DIR/bin/mvn package
cd $BASEDIR

cd $BASEDIR/ws/bestdog
$MVN_DIR/bin/mvn package
cd $BASEDIR

echo ""

echo "STARTING H2..."
open_tab h2 $SCRIPTS_PATH

echo "STARTING WS..."
open_tab ws $SCRIPTS_PATH

echo "STARTING ODE..."
open_tab ode $SCRIPTS_PATH


#firefox -new-tab http://localhost:8181/bestdog/ &
#firefox -new-tab http://localhost:8080/bpel/ &

#/bpel/bpel.sh
