FROM docker.artifactory.euw.platformservices.io/gradle:5.4.1-jdk8-alpine

USER root
RUN mkdir -p /app

WORKDIR /app
ADD src integration-test/src
ADD specs integration-test/specs

WORKDIR /app/integration-test
COPY build.gradle ./
COPY manifest.json ./

RUN apk add curl openssl
RUN curl -SsL https://downloads.gauge.org/stable | sh
RUN gauge telemetry off
RUN gauge install java
RUN gauge install html-report
RUN gauge install screenshot

RUN gradle -g .gradle build
ENTRYPOINT gradle -g .gradle gauge

CMD echo "Integration test container is up"
