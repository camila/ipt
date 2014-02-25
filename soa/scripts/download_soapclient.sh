#/bin/bash
if [ -d /tmp/_soapclient ]; then
echo "soapclient already installed"
else
JAVA_HOME=/usr/lib/jvm/java-7-oracle
cd $JAVA_HOME
sudo ln -s jre jdk
cd /tmp
mkdir _soapclient
cd soapclient
wget http://demo.360works.com/wsm/SOAPClient.zip
unzip SOAPClient.zip
rm SOAPClient.zip
fi
cd /tmp/soapclient/build
java -Djava.home=$JAVA_HOME/jdk/ -cp *.jar -jar SOAPClient.jar &
