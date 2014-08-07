#/bin/bash
JAVA_HOME=/usr/lib/jvm/java-7-oracle
SOAP_CLIENT=/tmp/SOAPClient.zip

if [ -L $JAVA_HOME/jre/bin/javac.exe ]; then
	echo "link already created"
else
	cd $JAVA_HOME
	sudo ln -s jre jdk
        cd jdk/lib
        sudo ln -s $JAVA_HOME/lib/tools.jar tools.jar
	cd ../bin
	sudo ln -s $JAVA_HOME/bin/javac javac.exe
fi



if [ -d /tmp/_soapclient ]; then
	echo "SOAPClient already installed"
else

cd /tmp

if [ -e $SOAP_CLIENT ]; then
	echo "client already present"
else
    wget http://demo.360works.com/wsm/SOAPClient.zip
fi

mkdir _soapclient
unzip $SOAP_CLIENT -d _soapclient

fi

cd /tmp/_soapclient/build
export CLASSPATH=$JAVA_HOME/jdk/lib:$JAVA_HOME/jdk/lib/tools.jar
java -Djava.home=$JAVA_HOME/jdk/ -cp *.jar:$JAVA_HOME/jdk/lib/tools.jar -jar SOAPClient.jar
