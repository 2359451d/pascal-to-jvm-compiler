#!/bin/bash
if [ ! -n "$1" ] ;then
    echo "PLEASE specify arguments!"
else
#echo "args: $1 $2"
  java -jar /usr/local/project/pascal-to-jvm-compiler.jar "$1" "$2"
fi
