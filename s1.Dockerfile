FROM bellsoft/liberica-openjdk-debian:11 AS builder
RUN apt install curl
RUN apt install unzip
RUN apt install  zip

RUN curl -s "https://get.sdkman.io" | bash
RUN /bin/bash -c "source /root/.sdkman/bin/sdkman-init.sh; sdk version; sdk install maven"

FROM builder AS maven

ENV APP=/home/app
RUN mkdir -p APP
WORKDIR $APP

ADD pom.xml $APP
RUN /bin/bash -c "source $HOME/.sdkman/bin/sdkman-init.sh && mvn verify --fail-never"
ADD . $APP

RUN /bin/bash -c "source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -f /home/app/pom.xml -B clean package"

FROM bellsoft/liberica-openjdk-alpine-musl:11
ENV NAME_APP=storage2-java-1.jar

COPY --from=maven "/home/app/target/$NAME_APP" spring-boot.jar
CMD [ "sh", "-c", "java -jar spring-boot.jar " ]


# docker build -t storage2s1 -f s1.Dockerfile .
# docker build -t storage2s1 -f s1.Dockerfile . | tee s1.build.log