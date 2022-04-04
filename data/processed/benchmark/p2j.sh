#!/bin/bash

# java -XX:+TieredCompilation -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2

# start=`date +%s%3N`

# java -Xcomp -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2
java -server -jar pascal-to-jvm-compiler-jar-with-dependencies.jar $1 $2

# end=`date +%s%3N`

# if [ $1 = "run" ]; then
#     echo `expr $end - $start`
#     exit 1
# fi

# full_file_name=$2
# prefix=`echo $full_file_name | sed -r "s/(.*)\.pas/\1/g"`

# if [ $1 = "compile" ]; then
#     compileTime=$[end-start]
#     # echo `expr $compileTime`

#     start=`date +%s%3N`
#     java `echo "${prefix^}"` > /dev/null
#     end=`date +%s%3N`
#     runTime=$[end-start]
#     echo `expr $compileTime + $runTime`
# fi