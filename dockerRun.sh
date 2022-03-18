#!/bin/bash

# Usage: ./dockerRun.sh <command> <absolute_path_to_the_file>
# Available Commands:
# - parse
# - check
# - run

_command=$1
_fullPath=$2
_path=${_fullPath%/*}
_fileName=${_fullPath##*/}

#echo $_path
#echo $_fileName

docker run --rm -v "$_path":/res \
 --name ptj barlinbento/pascal-to-jvm-compiler "$_command" \
 "$_fileName"