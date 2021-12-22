#!/bin/bash
# run example (Once finished, container would be destroyed directly)
# start a new container, assuming source files is from current path
# default container workdir is /usr/local/project/resources
docker run --rm -v $(pwd)/:/usr/local/project/resources \
 --name ptj2 pascal-to-jvm-compiler parse \
 src/test/resources/driver/testPascalCompilerDriver/testCheckArguments/testArguments.pas