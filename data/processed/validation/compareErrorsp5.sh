#!/bin/bash

# ===============Compile Time Errors Comparison==================
# compare the compiler output with expected error files (generated from "gold standard compiler")

# ptj="java -jar ./target/pascal-to-jvm-compiler-jar-with-dependencies.jar"
ptj="./p2j.sh"
ptjdocker="./dockerRun.sh"
p5="compile"
gpc="gpc"
fpc="fpc"
fpciso="fpc -Miso"

compiler_alias=$1
compiler=""
command=""
filename=""
path=""

if [[ $1 = "ptj" ]] || [[ $1 = "ptjdocker" ]]; then

    if [[ $1 = "ptj" ]];then
        compiler=$ptj
    elif [[ $1 = "ptjdocker" ]]; then
        compiler=$ptjdocker
    fi

    command=$2
    shift 2
    filename=$@
    if [[ -z $filename ]]; then
        echo "Invalid arugments. Usage: ptj <command> <file_path>"
        exit 1
    fi
    if [[ $command != "run" ]] && [[ $command != "parse" ]] && [[ $command != "check" ]]; then
        echo "Invalid arugments. Usage: ptj <command> <file_path>"
        exit 1
    fi

elif [[ $1 = "gpc" ]]; then
    compiler=$gpc
    shift
    filename=$@
elif [[ $1 = "fpc" ]]; then
    compiler=$fpc
    shift
    filename=$@
elif [[ $1 = "fpciso" ]]; then
    compiler=$fpciso
    shift
    filename=$@
elif [[ $1 = "p5" ]]; then
    compiler=$p5
    shift
    filename=$@
else
    echo "Please specify invalid compiler: fpc, gpc, p5, ptj"
    exit 1
fi

echo "compiler: $compiler"
echo "command: $command"
echo "filename: $filename"

# **********Initialising Operation
# perform flushing
# regenerate the comparelog for current CUT
# if [[ -e ./comparelog.patch ]];then
echo "**************************************************" > ./comparelog.patch
echo "Log file generated for compiler [$compiler_alias]" >> ./comparelog.patch
echo "**************************************************" >> ./comparelog.patch
echo "" >> ./comparelog.patch
# fi
# **********

testcounter=0
testmatch=0
testunmatch=0
startTime=`date +%Y%m%d-%H:%M:%S`
startTime_s=`date +%s`
# compile each Pascal source file
for i in $filename
do
    if [[ ! -e $i ]]; then
        # if file does not exist
        continue
    fi

    if [[ $i == *.pas ]] ;then
        if [[ $compiler = $p5 ]]; then
            fileprefix=`echo $i | grep -Eo ".*[[:digit:]]+"`
            eval $compiler $command $fileprefix &> $i.lst
        else
            eval $compiler $command $i &> $i.lst
        fi

        testcounter=$((testcounter+1))
        # prepare & process expected error output
        if [[ -e $i.err ]];then
            (cat $i.err | sort -nu) > $i.err_
            # echo "expected error ouput trace:" && cat $i.err_
        else
            # no expected error output found for current file
            # so expected trace would be empty
            echo "" > $i.err_
            # echo "expected error ouput trace:" && cat $i.err_
        fi

        # prepare & process CUT(compiler under test) output
        # "sort -nu" to supress and sort error line
        # in order to compare with expected error file 
        if [[ $compiler = $p5 ]]; then
            # make the p5 compile output into other file
            fileprefix=`echo $i | grep -Eo ".*[[:digit:]]+"`
            cp $fileprefix.err $i.lst

            (cat $i.lst | awk 'NF' | sed -r "s/[[:space:]]*//g" | grep -Eo "([[:digit:]]+\*\*+.*)" | sed -r "s/([[:digit:]]+)\*+.*/\1/g" | sort -nu ) > $i.lst_

            cat $i.lst
            cat $i.err_

            # clean up original auxilary files generated by p5
            rm -f $fileprefix.err
            rm -f $fileprefix.lst

        elif [[ $compiler = $ptj ]] || [[ $compiler = $ptjdocker ]]; then

            (cat $i.lst | grep "\[ERROR\]" | cut -d ' ' -f 2 | sed -r "s|([[:digit:]]*):.*|\1|g" | sort -nu) > $i.lst_

        elif [[ $compiler = $gpc ]]; then

            (cat $i.lst | grep -Eo "[[:digit:]]+:\serror" | sed -r "s/([[:digit:]]+):.*/\1/g" | sort -nu) > $i.lst_

        elif [[ $compiler = $fpc ]] || [[ $compiler = $fpciso ]];then

            (cat $i.lst | grep -Eo ".*\sError:.*" | sed -r "s/.*\(([[:digit:]]+),[[:digit:]]+.*/\1/g" | sort -nu) > $i.lst_
        fi

        noError=`cat $i.lst_`
        if [[ -z $noError ]];then
            if [[ -z `cat $i.err_` ]];then
                 testmatch=$((testmatch+1))
                echo "No diff found: $i passed."
                 # clean up
                rm -f $i.lst_
                rm -f $i.err_
                continue
            fi
        fi
        echo " DIFFFFFFF???? i.err_" `cat $i.err_`
        echo " DIFFFFFFF???? i.lst_" `cat $i.lst_`
        # diff $i.err_ $i.lst_

        # echo "actual actual error ouput trace:" && cat $i.lst_
        diff $i.err_ $i.lst_ &> /dev/null
        ret=$? # whether has difference

        if [[ $ret -eq 0 ]]; then
            testmatch=$((testmatch+1))
            echo "No diff found: $i passed."
        else
            testunmatch=$((testunmatch+1))
            # record any difference
            echo "Diff recorded: $i failed."
            echo "===Unmatched Source Name: $i===" >> ./comparelog.patch
            diff -rupP $i.err $i.lst >> ./comparelog.patch
            echo "" >> ./comparelog.patch
        fi

        # clean up
        rm -f $i.lst_
        rm -f $i.err_

    fi
done

endTime=`date +%Y%m%d-%H:%M:%S`
endTime_s=`date +%s`
sumTime=$((endTime_s-startTime_s))

# generate test cases count, matched count, Unmatched count
info="Tese Case Count: [$testcounter], Matched: [$testmatch], Unmatched: [$testunmatch]"
timeusage=`echo "$startTime ---> $endTime" "Total:$sumTime seconds finished"`
echo -e "$(head -n2 ./comparelog.patch)\n$info\n$timeusage\n$(tail -n+3 ./comparelog.patch)" > ./comparelog_$compiler_alias.patch

# cleanup temp log file
rm -f ./comparelog.patch

echo $info
echo $timeusage