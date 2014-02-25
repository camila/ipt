#!/bin/bash
TMP_ODE_DIR=/tmp/_ode
WSDL_URL=http://localhost:8181/bestdog/

if [ "$1" == "wsdl" ]; then

  cd bpelContent
  wget $WSDL_URL/Estoque?wsdl -O Estoque.wsdl
  wget $WSDL_URL/Milhagem?wsdl -O Milhagem.wsdl


else

  cp -r bpelContent/ $TMP_ODE_DIR/WEB-INF/processes/


fi
