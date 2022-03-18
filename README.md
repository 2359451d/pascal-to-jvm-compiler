# Pascal to JVM compiler

[![Java CI with Maven, Docker (build & test & deploy)](https://github.com/2359451d/L4-Source-Repo/actions/workflows/maven.yml/badge.svg)](https://github.com/2359451d/L4-Source-Repo/actions/workflows/maven.yml)

> A compiler for the classic programming language Pascal (or another language), using Java Virtual Machine code as the target language.

# --

submission details see

* [issue](https://github.com/2359451d/L4-Dissertation-Repo/issues/4)
* [moodle](https://moodle.gla.ac.uk/course/view.php?id=30221#section-7)

## Active Issues

See

* [issues](https://github.com/2359451d/L4-Source-Repo/issues) or
* [board](https://github.com/2359451d/L4-Source-Repo/projects/1)

<!-- ## Progress

Basic Progress:

* syntactic analysis(might refine): ![syntactic_analysis](https://progress-bar.dev/100/?title=done)
* contextual analysis: ![contextual_analysis](https://progress-bar.dev/28/?title=WIP)
  * [Refer](http://web.fis.unico.it/local/SunDocs/pascal/lang_ref/index.html) as both standard & nonstandard information given
  * Remark: predefined RTL varies in different Pascal extension
    * [ISO 7185:1990 standard pascal](https://rti.etf.bg.ac.rs/rti/ir1p1/materijali/iso7185.pdf)
    * [FPC RTL docs](https://wiki.freepascal.org/RTL)
      * standard Pascal services
      * extended Pascal services
      * Object Pascal
      * Delphi
      * Turbo Pascal
      * GNU Pascal
      * Mac Pascal
    * Turbo Pascal - Borland's dialect of the Pascal
    * GNU Pascal - ISO 7185, ISO 10206 Extended Pascal, Borland Pascal 7.0, parts of Borland Delphi, Mac Pascal & Pascal-SC(PXSC)
* code generation: ![contextual_analysis](https://progress-bar.dev/0/)
* status report
* dissertation

Project Extension Progress:

* OO Pascal feature
* code optimisation
  * intermediate code optimisation (seems some optimisation are handled to JVM)
* command tool
* docker & npm deployment
* other possible extension?
  * AST visualisation
  * etc. -->

# Readme

<!-- Put a brief description of your code here. This should at least describe the file structure. -->

temporary project structure see `tree.txt`. **DESCRIPTION TBC**

## Build instructions

<!-- **You must** include the instructions necessary to build and deploy this project successfully. If appropriate, also include
instructions to run automated tests. -->

Building steps (with Maven) are listed below. You may also run this project without building, using Jar or Docker.

### Requirements

<!-- List the all of the pre-requisites software required to set up your project (e.g. compilers, packages, libraries, OS, hardware) -->

<!-- For example: -->

<!-- * Python 3.7 -->
<!-- * Packages: listed in `requirements.txt` -->
<!-- * Tested on Windows 10 -->

<!-- or another example: -->

<!-- * Requires Raspberry Pi 3 -->
<!-- * a Linux host machine with the `arm-none-eabi` toolchain (at least version `x.xx`) installed -->
<!-- * a working LuaJIT installation > 2.1.0 -->

* Java/JDK `11` (**Minimum**)
* Maven `xxx`
* Antlr `4.9.1`
* Docker `xxx`
* Test on linux/amd64, win10/amd64

### Local Build with Maven

TBC

### Run Without Build - Java Jar

File `target/pascal_jvm_compiler-jar-with-dependencies.jar` is ready to use which is exported using Maven with (**minimum**) JDK version of `11`

To run the compiler in shell:

* **Remark**: run the command below only where `jar` file exists or create a alias beforehand like `alias ptj="java -jar ./target/pascal-to-jvm-compiler-jar-with-dependencies.jar"`

```bash
# Usage:
# Available command:
#  - parse
#  - check
#  - compile
java -jar pascal-to-jvm-compiler-jar-with-dependencies.jar <command> <path>
```

You may run a quick example using `./testArguments.pas` under the project root, to check whether it works or not:

```bash
# Parsing example Pascal source program
# If works, you will see a syntactic analysis results with 0 errors
java -jar pascal-to-jvm-compiler-jar-with-dependencies.jar parse ./testArguments.pas
```

If you have already created an alias, then directly run something like:

```bash
# Usage: [alias-name] <command> <path>
ptj parse ./testArguments.pas
```

### Run Without Build - Docker Container

[![DockerHub_url](https://img.shields.io/badge/DockerHub-pascal--to--jvm--compiler-blue.svg?style=flat-square&logo=docker&labelColor=grey)](https://hub.docker.com/r/barlinbento/pascal-to-jvm-compiler) - [link](https://hub.docker.com/r/barlinbento/pascal-to-jvm-compiler)

Environment Information:

* OpenJDK build - `eclipse-temurin:17`
* Antlr 4.9.1

Platforms are limited, supported platforms are listed below ([full available platforms see](https://github.com/docker/setup-qemu-action)):

* `linux/amd64`
* `linux/arm64`
* `linux/arm/v7`
* ...

Make sure you have Docker installed.

First pull the image from Dockerhub.

```bash
docker pull barlinbento/pascal-to-jvm-compiler:latest
```

Then you may run a new container each time to compile the source:

* **Remark**: specify the host dir where your source files exist, the example below will take the current working directory. The volume must be mounted to `/usr/local/project/resources` (fixed in `Dockerfile`)

```bash
# docker run --rm -v <source-path>/:/usr/local/project/resources \
#  pascal-to-jvm-compiler <command> <source-filename>

docker run --rm -v $(pwd):/usr/local/project/resources \
 pascal-to-jvm-compiler parse testArguments.pas
```

It is recommended to create an alias, **make sure you call the alias under the path where Pascal source programs exist, if choose `$(pwd)` as host path**

```bash
# Create an alias
# Then usage: ptj <command> <path>
alias ptj="docker run --rm -v $(pwd):/usr/local/project/resources pascal-to-jvm-compiler"

# Then quick run the compiler
ptj parse ./testArguments.pas
```

### Build steps

<!-- List the steps required to build software.

Hopefully something simple like `pip install -e .` or `make` or `cd build; cmake ..`. In
some cases you may have much more involved setup required. -->

TBC

### Test steps

<!-- List steps needed to show your software works. This might be running a test suite, or just starting the program; but something that could be used to verify your code is working correctly.

Examples:

* Run automated tests by running `pytest`
* Start the software by running `bin/editor.exe` and opening the file `examples/example_01.bin` -->

TBC

* mvn test

test suites and resources

* main test suites and test cases can be found under `src/test/java`
* basic test suites are designed for testing:
  * **driver arguments**
  * **parse** (syntactic analysis)
  * **check** (semantic analysis)
  * **run** (code generation)
* resources of each purpose can be found within corresponding directories under `src/test/resources`
  * e.g. dir of source testing pascal program resources of semantic analysis - `src/test/resources/driver/testPascalCompilerDriver/testCheck`

# CI/CD

# --------------DRAFT------------------

# Regenerate Compiler Components (Lexer & Parser)

source componets (visitor pattern) already given in this repo `src\main\java\ast` but still can regenerate these components using ANTLR4 if you want or the grammar file(`.g4`) has been changed

## ANTLR4.9.1 Dockerfile for Pascal grammar

QUICK WAY: using antlr by docker, image env

* JDK8
* ANTLR 4.9.1

TBC

Pull from [![DockerHub_url](https://img.shields.io/badge/DockerHub-antlr4.9.1--pascal-blue.svg?style=flat-square&logo=docker&labelColor=grey)](https://hub.docker.com/r/barlinbento/antlr4.9.1-pascal), make sure you are at the source folder

```bash
docker pull barlinbento/antlr4.9.1-pascal
```

Or build Dockerfile locally by script

```bash
# win powershell/cmd usage
$ .\docker_run.sh
```

```bash
# linux usage
$ ./docker_run
```

# Note

You must include a `README.md` file and a `manual.md` file with your source code.

* There are example README.md file and manual.md files in the SRC folder of the project template at the foot of this page.

# ==============Template Organise TBC==============

# Readme

Put a brief description of your code here. This should at least describe the file structure.

## Build instructions

**You must** include the instructions necessary to build and deploy this project successfully. If appropriate, also include
instructions to run automated tests.

### Requirements

List the all of the pre-requisites software required to set up your project (e.g. compilers, packages, libraries, OS, hardware)

For example:

* Python 3.7
* Packages: listed in `requirements.txt`
* Tested on Windows 10

or another example:

* Requires Raspberry Pi 3
* a Linux host machine with the `arm-none-eabi` toolchain (at least version `x.xx`) installed
* a working LuaJIT installation > 2.1.0

### Build steps

List the steps required to build software.

Hopefully something simple like `pip install -e .` or `make` or `cd build; cmake ..`. In
some cases you may have much more involved setup required.

### Test steps

List steps needed to show your software works. This might be running a test suite, or just starting the program; but something that could be used to verify your code is working correctly.

Examples:

* Run automated tests by running `pytest`
* Start the software by running `bin/editor.exe` and opening the file `examples/example_01.bin`