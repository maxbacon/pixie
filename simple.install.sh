#!/bin/sh
mvn clean compile assembly:single
mkdir $HOME/jars
cp target/pixie-*.jar $HOME/jars
echo "install scripts?"
sudo cp scripts/pixie-* /usr/bin
