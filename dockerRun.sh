#!/bin/bash

# Usage: ./dockerRun.sh <command> <absolute_path_to_the_file>
# Available Commands:
# - parse
# - check
# - run

_command=$1
_fullPath=$2
if [[ -z $_command ]] || ([[ $_command != "run" ]] && [[ $_command != "parse" ]] && [[ $_command != "check" ]]);then
    echo "Please specify valid command."
    echo "Available command: parse, check, run"
    exit 1
fi

if [[ -z $_fullPath ]] || [[ ! -e $_fullPath ]];then
    echo "Please specify valid path."
    exit 1
fi

_path=`echo $_fullPath | grep -Eo ".*/"`
_fileName=`echo $_fullPath | grep -Eo "[^/]*.pas"`

# echo path: $_path
# echo filename: $_fileName

# if [[ ! -d $_path ]] ;then
#     echo not a valid dir
#     exit 1
# fi

if [[ $_path =~ [.].* ]] || [[ $_path =~ [.][/].* ]];then
    curdir=`pwd`
    _path=`echo $_path | sed -r "s|\.|$curdir|g"`
    # echo $_path
fi

if [[ -z $_path ]];then
   _path=`pwd`
fi

docker run --rm -v "$_path":/res \
 --name ptj barlinbento/pascal-to-jvm-compiler "$_command" \
 "$_fileName"