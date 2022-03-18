#!/bin/bash
# run example (Once finished, container would be destroyed directly)
# start a new container, assuming source files is from current path

# please run this example under cloned root directory
# default container workdir is /res

echo "Syntactic Analysis Example Running..."

echo
docker run --rm -v $(pwd)/:/res \
 --name ptj barlinbento/pascal-to-jvm-compiler parse \
 src/test/resources/driver/testPascalCompilerDriver/testParse/testParseWithSuccess/helloWorld.pas
echo

echo "Contextual Analysis Example Running..."

echo
docker run --rm -v $(pwd)/:/res \
 --name ptj barlinbento/pascal-to-jvm-compiler check \
src/test/resources/driver/testPascalCompilerDriver/testCheck/testCheckWithError/arithOperator001.pas
echo
