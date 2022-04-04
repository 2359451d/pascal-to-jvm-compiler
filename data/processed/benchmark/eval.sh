# echo "      time" | awk '{$1=$1}1' OFS="," > log20201113.csv
# echo "      this is time" | awk '{$1=$1}1' OFS="," >> log20201113.csv

#!/bin/bash

fpciso=`fpc -MISO`
ptj=`./p2j.sh`

compiler=""
full_file_name=$2
prefix=`echo $full_file_name | sed -r "s/(.*)\.pas/\1/g"`

if [ $1 = "fpc" ]; then
    echo "time" | awk '{$1=$1}1' OFS="," > fpc_$prefix.csv
    for counter in `seq 1 20`
    do
    start=`date +%s%3N`
    fpc -MISO $full_file_name > /dev/null
    $(pwd)/$prefix > /dev/null
    end=`date +%s%3N`

    rm -f $(pwd)/$prefix
    rm -f $(pwd)/$prefix.o
    echo `expr $end - $start`
    echo `expr $end - $start` | awk '{$1=$1}1' OFS="," >> fpc_$prefix.csv    
    done
elif [ $1 = "ptj" ]; then

    echo "time" | awk '{$1=$1}1' OFS="," > ptj_$prefix.csv

    for counter in `seq 1 100`
    do
        start=`date +%s%3N`
        ./p2j.sh run $full_file_name > /dev/null
        end=`date +%s%3N`

        rm -f $(pwd)/$prefix.class
        echo `expr $end - $start`
        echo `expr $end - $start` | awk '{$1=$1}1' OFS="," >> ptj_$prefix.csv  
    done

elif [ $1 = "gpc" ]; then 
    echo "time" | awk '{$1=$1}1' OFS="," > gpc_$prefix.csv

    for counter in `seq 1 20`
    do
    start=`date +%s%3N`
    gpc $full_file_name > /dev/null
    $(pwd)/a.out > /dev/null
    end=`date +%s%3N`

    rm -f $(pwd)/a.out
    echo `expr $end - $start`
    echo `expr $end - $start` | awk '{$1=$1}1' OFS="," >> gpc_$prefix.csv  
    done
elif [ $1 = "p5" ]; then 
    echo "time" | awk '{$1=$1}1' OFS="," > p5_$prefix.csv

    for counter in `seq 1 20`
    do
    start=`date +%s%3N`
    p5 $prefix > /dev/null
    end=`date +%s%3N`

    rm -f $(pwd)/$prefix.p5
    echo `expr $end - $start`
    echo `expr $end - $start` | awk '{$1=$1}1' OFS="," >> p5_$prefix.csv  
    done
fi

# echo Execution time was `expr $end - $start` nanoseconds.

# echo "      time" | awk '{$1=$1}1' OFS="," > log20201113.csv
# echo `      expr $end - $start` | awk '{$1=$1}1' OFS="," >> log20201113.csv
