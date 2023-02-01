FROM sapmachine:19.0.2
ARG jarfile
RUN rm /bin/sh && ln -s /bin/bash /bin/sh
# install curl
RUN apt-get update \
    && apt-get install -y curl \
    && apt-get -y autoclean

# install nvm + node
ENV NVM_DIR /usr/local/nvm
ENV NODE_VERSION 16.14.0

RUN curl --silent -o- https://raw.githubusercontent.com/creationix/nvm/v0.31.2/install.sh | bash


RUN source $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default

# add node and npm to path so the commands are available
ENV NODE_PATH $NVM_DIR/v$NODE_VERSION/lib/node_modules
ENV PATH $NVM_DIR/versions/node/v$NODE_VERSION/bin:$PATH
# install serve
RUN npm install -g serve

# copy apps to run
RUN mkdir app
WORKDIR app
COPY ./frontend ./
RUN npm install
COPY ./AskMateREST/target/${jarfile} ./app.jar
RUN chmod 777 ./app.jar

COPY ./wrapper.sh ./
RUN chmod -x wrapper.sh

# Add to keystore
COPY ./myCertificate.crt ./myCertificate.crt
RUN keytool -importcert -noprompt -file myCertificate.crt -alias springboot -cacerts


ENV spring.datasource.url null
ENV spring.datasource.driver-class-name null
ENV spring.jpa.properties.hibernate.dialect null
ENV spring.datasource.username null
ENV spring.datasource.password null
ENV jwt_secret null

EXPOSE 3000
#EXPOSE 8080
EXPOSE 1433
CMD bash wrapper.sh
#ENTRYPOINT ["tail", "-f", "/dev/null"]
