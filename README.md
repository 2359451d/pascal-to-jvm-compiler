# L4-Source-Repo

repo for level 4 project source code

submission details see

* [issue](https://github.com/2359451d/L4-Dissertation-Repo/issues/4)
* [moodle](https://moodle.gla.ac.uk/course/view.php?id=30221#section-7)

# Progress

Basic Progress:

* syntactic analysis(might refine): ![syntactic_analysis](https://progress-bar.dev/100/?title=done)
* contextual analysis: ![contextual_analysis](https://progress-bar.dev/28/?title=WIP)
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
  * etc.

# Regenerate Compiler Components (Lexer & Parser)

source componets (visitor pattern) already given in this repo `src\main\java\ast_visitor` but still can regenerate these components using ANTLR4 if you want or the grammar file(`.g4`) has been changed

## ANTLR4.9.1 Dockerfile for Pascal grammar

QUICK WAY: using antlr by docker, image env

* JDK8
* ANTLR 4.9.1

TBC

Pull from [![DockerHub_url](https://img.shields.io/badge/DockerHub-antlr4.9.1--pascal-blue.svg?style=flat-square&logo=docker&labelColor=grey)](https://hub.docker.com/r/barlinbento/antlr4.9.1-pascal), make sure you are at the source  folder

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