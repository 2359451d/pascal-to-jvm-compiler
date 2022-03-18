#!/bin/bash
if [ -z "$1" ] ;then
    echo "Please specify command!"
else
#echo "args: $1 $2"
  java -jar /usr/local/project/pascal-to-jvm-compiler.jar "$1" "$2"
fi
