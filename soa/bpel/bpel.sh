#!/bin/bash
TMP_ODE_DIR=target/ode

WSDL_URL=http://localhost:8181/bestdog/

if [ "$1" == "wsdl" ]; then

  cd bpelContent
  wget $WSDL_URL/Estoque?wsdl -O Estoque.wsdl
  wget $WSDL_URL/Milhagem?wsdl -O Milhagem.wsdl


else

  rm -rf $TMP_ODE_DIR/WEB-INF/processes/bpelContent
  sleep 1
  cp -r bpelContent/ $TMP_ODE_DIR/WEB-INF/processes/


fi
