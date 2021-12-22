#FROM adoptopenjdk/openjdk8
FROM eclipse-temurin:17

# label information could be checked through command 'docker inspect' 
LABEL version="1.0"
#LABEL jdk_version='8'
LABEL jdk_version='17'
LABEL antlr_version='4.9.1'
LABEL author='2359451d'
LABEL maintainer_email1="2359451d@student.gla.ac.uk"
LABEL maintainer_email2="bentoremains@gmail.com"

WORKDIR /usr/local/lib
RUN curl -O https://www.antlr.org/download/antlr-4.9.1-complete.jar
#  add antlr runtime jar to container CLASSPATH
ENV CLASSPATH=/usr/local/lib/antlr-4.9.1-complete.jar:$CLASSPATH
# create aliases for the ANTLR Tool, and TestRig(AST visualisation) - only works for the interactive container
RUN echo 'alias antlr4="java -Xmx500M -cp '/usr/local/lib/antlr-4.9-complete.jar:$CLASSPATH' org.antlr.v4.Tool"' >> ~/.bashrc  \
    && echo 'alias grun="java -Xmx500M -cp "/usr/local/lib/antlr-4.9-complete.jar:$CLASSPATH" org.antlr.v4.gui.TestRig"' >> ~/.bashrc \
    && echo '#!/bin/bash\necho java -Xmx500M -cp '/usr/local/lib/antlr-4.9-complete.jar:$CLASSPATH' org.antlr.v4.Tool' > /usr/bin/antlr4 \
    && chmod +x /usr/bin/antlr4 \
    # VOLUME USAGE: make new directory for the grammar file(s) & lexer/parser generated
    && mkdir -p /usr/local/grammar \
    && mkdir -p /usr/local/ast

#WORKDIR /usr/local/grammar
RUN mkdir -p /usr/local/project
WORKDIR /usr/local/project
ADD ./target/pascal_jvm_compiler-jar-with-dependencies.jar pascal_jvm_compiler.jar
ADD ./target/pascal_jvm_compiler.jar _pascal_jvm_compiler.jar

ENTRYPOINT ["/bin/bash","-c"]
CMD ["echo 'PLEASE specify args'"]
