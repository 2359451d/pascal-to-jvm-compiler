#!/bin/bash

flag=$1
path=$2

echo flag $flag
echo path $path

# default: only clean up expected error files
if [[ -z $path ]] && [[ -z $flag ]]; then
  path=$flag
  rm -f *.pas.err
elif [[ $flag == -[aA] ]]; then
  if [[ ! -z $path ]];then
    rm -f $path/*.pas.err
    rm -f $path/*.pas.lst
  else
    rm -f *.pas.err
    rm -f *.pas.lst
  fi
fi