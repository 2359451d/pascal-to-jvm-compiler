#!/bin/bash

# java -XX:+TieredCompilation -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2
# java -Xcomp -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2

# java -Xcomp -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2

start=`date +%s%3N`
./p2j.sh compile benchmark4.pas > /dev/null
java -server Benchmark4 > /dev/null
end=`date +%s%3N`
echo `expr $end - $start`
# for counter in `seq 1 20`
    # do