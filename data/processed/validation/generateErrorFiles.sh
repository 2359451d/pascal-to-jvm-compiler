#!/bin/bash

# generate the error files using corresponding "gold standard compiler"

# default using free Pascal with ISO7185(standard Pascal) mode
compiler=fpc
flags='Miso'
filename=$*

if [[ -z $filename ]]; then
    echo "Please specify valid path"
    exit 1
fi

startTime=`date +%Y%m%d-%H:%M:%S`
startTime_s=`date +%s`

# compile each Pascal source file
for i in $filename
do
    # echo $i
    #  capture error files, sort and supress errors (i.e. keep error line unique)
    if [[ $i == *.pas ]] ;then
        $compiler -$flags $i > $i.err_
        (head -n -2 $i.err_ | grep 'Error\|Fatal' ) > $i.err

        noError=`cat $i.err`
        if [[ ! -z $noError ]] || [[ ! -z $noError2 ]];then    
            # if should compiled with errors
            echo "error file generating: $i ..."
        else
            # expected compiled with no errors,
            # remove the empty expected error file 
            rm $i.err
        fi
        
        # clean up
        rm $i.err_ 
    fi
done

endTime=`date +%Y%m%d-%H:%M:%S`
endTime_s=`date +%s`

sumTime=$((endTime_s-startTime_s))

echo "$startTime ---> $endTime" "Total:$sumTime seconds"

# # linecount=sudo wc -l $filename.err
# # linecount_=$($(linecount)-2)
# # echo linecount_
# # sed "2,\$d" $filename.err
