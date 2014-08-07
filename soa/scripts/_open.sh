#!/bin/bash
TERMINAL_CMD="${1-xfce4-terminal --tab --title V_TITLE --drop-down -e}"

open_tab() {

 CMD="bash -c 'V_CMD; bash'"
 CMD_PARSED=`echo $CMD | sed "s#V_CMD#$2/$1#"`
 TERMINAL_CMD_PARSED=`echo $TERMINAL_CMD | sed "s/V_TITLE/$1/"`

 echo "[$TERMINAL_CMD \"$CMD_PARSED\"]"
 read -p "(press ENTER to confirm)"
 $TERMINAL_CMD_PARSED "$CMD_PARSED"

}
