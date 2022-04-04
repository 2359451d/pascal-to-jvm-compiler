## Data

Note Some data was missing.

`validation` folder contains scripts using white, black boxing techniques to gather data.

1. generate error file

* `generateErrorFile.sh` - using FPC
* `generateErrorFilep5.sh` - using Pascal-P5

2. compare error 

* `compareErrors.sh` - compare CUT output with expected files from FPC
* `compareErrorsp5.sh` - compare CUT output with expected files from Pascal-P5

`benchmark` folder contains scripts and data evaluated from 3 benchmark programs

* results can be found in `benchmark1.xlsx` and so on
* `eval.sh` to run the evaluation scripts and measure the running time with FPC, GPC, Pascal-P5 compilers
  * note the final data was gathered and concatenated manually

