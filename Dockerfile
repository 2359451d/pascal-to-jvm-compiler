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

# Install vim-tiny
RUN apt-get update && apt-get -y install vim-tiny

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
#RUN mkdir -p /usr/local/project && mkdir -p /usr/local/project/resources
RUN mkdir -p /usr/local/project && mkdir -p /usr/local/project/resources && mkdir -p /res

# set up project jar file
WORKDIR /usr/local/project
ADD ./target/pascal-to-jvm-compiler-jar-with-dependencies.jar pascal-to-jvm-compiler.jar
ADD ./target/pascal-to-jvm-compiler.jar _pascal-to-jvm-compiler.jar

WORKDIR /usr/local/project/resources
# set up jar file entrance script
COPY ./usage.sh ./usage.sh
# modify permission
RUN chmod +x usage.sh

WORKDIR /res

#ENTRYPOINT ["/bin/bash","-c", "java -jar pascal_jvm_compiler.jar"]
ENTRYPOINT ["/usr/local/project/resources/usage.sh"]
#ENTRYPOINT ["/bin/bash"]
#CMD ["echo 'PLEASE specify args'"]
