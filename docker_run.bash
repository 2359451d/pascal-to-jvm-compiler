#!/bin/bash

# ===========antlr4 docker image building & run script (POWERSHELL)===========
# build dockerfile & tag into local images list
# run antlr4 container to (re)generate the compiler components 
#   - add new volume for the container usage
#       - for grammar
#       - for generated compiler components
#   - output dir should be fixed
#   - ideally, only provide the visitor pattern parser(contrast to the listener pattern)

# docker image prune --force
docker build --no-cache -t pascal_to_jvm/antlr4 .
# docker run -it --rm --name myantlr antlr4.9.1 /bin/bash -c "java org.antlr.v4.Tool"
docker run -v ${pwd}\src\main\java\ast_visitor:/usr/local/ast -v ${pwd}\src\main\java\grammar:/usr/local/grammar -it --rm --name myantlr antlr4.9.1 'java org.antlr.v4.Tool -no-listener -o /usr/local/ast /usr/local/grammar/Pascal.g4' # TODO: fixed output & input path, left for now  

# docker run -it --rm --name myantlr antlr4.9.1 \
#  -v $(pwd)\src\main\java\ast_visitor:/usr/local/ast \
#  -v $(pwd)src\main\java\grammar:/usr/local/grammar /bin/bash